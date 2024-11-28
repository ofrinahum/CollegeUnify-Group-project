package com.CollegeUnify.project.TaskManagement.Task_Mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.CollegeUnify.project.TaskManagement.Task_Model.Task;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TasksDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Fetch all tasks with repeat days for a specific user
    public List<Task> findByUserId(Long userId) {
        String query = """
            SELECT 
                t.id, t.title, t.description, t.priority, t.type, t.due_date, t.due_time, t.event_date,
                t.start_time, t.end_time, t.repeat_pattern, t.start_date,
                t.end_date, t.completed, t.completed_at, t.created_at, t.updated_at,
                COALESCE(GROUP_CONCAT(rd.day), '') AS repeat_days
            FROM task t
            LEFT JOIN repeat_days rd ON t.id = rd.task_id
            WHERE t.user_id = ?
            GROUP BY 
                t.id, t.title, t.description, t.priority, t.type, t.due_date, t.due_time, t.event_date,
                t.start_time, t.end_time, t.repeat_pattern, t.start_date,
                t.end_date, t.completed, t.completed_at, t.created_at, t.updated_at
        """;
    
        // Use jdbcTemplate to query the database
        return jdbcTemplate.query(query, new TaskRowMapper(), userId);
    }
    

    // Fetch only incomplete tasks for a specific user
public List<Task> findIncompleteByUserId(Long userId) {
    String query = """
        SELECT 
            t.id, t.title, t.description, t.priority, t.type, t.due_date, t.due_time, t.event_date,
            t.start_time, t.end_time, t.repeat_pattern, t.start_date,
            t.end_date, t.completed, t.completed_at, t.created_at, t.updated_at,
            COALESCE(GROUP_CONCAT(rd.day), '') AS repeat_days
        FROM task t
        LEFT JOIN repeat_days rd ON t.id = rd.task_id
        WHERE t.user_id = ? AND t.completed = false
        GROUP BY 
            t.id, t.title, t.description, t.priority, t.type, t.due_date, t.due_time, t.event_date,
            t.start_time, t.end_time, t.repeat_pattern, t.start_date,
            t.end_date, t.completed, t.completed_at, t.created_at, t.updated_at
    """;

    return jdbcTemplate.query(query, new TaskRowMapper(), userId);
}

// Mark a task as incomplete
public boolean markAsIncomplete(Long taskId) {
    String updateSQL = "UPDATE task SET completed = false, updated_at = NOW() WHERE id = ?";
    return jdbcTemplate.update(updateSQL, taskId) > 0;
}


    // Fetch a specific task by ID
    public Task findById(Long taskId) {
        String query = """
            SELECT t.*, GROUP_CONCAT(rd.day) AS repeat_days
            FROM task t
            LEFT JOIN repeat_days rd ON t.id = rd.task_id
            WHERE t.id = ?
            GROUP BY t.id
        """;
    
        return jdbcTemplate.queryForObject(query, new TaskRowMapper(), taskId);
    }
    
    // Add a new task to the database
    public boolean save(Task task, Long userId) {
        if (task.getId() != null) {
            throw new IllegalStateException("Cannot save a task with an existing ID. Use update instead.");
        }
    
        String insertSQL = """
            INSERT INTO task (title, description, priority, type, due_date, event_date, start_time, end_time, due_time, repeat_pattern, start_date, end_date, completed, created_at, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)
        """;
    
        KeyHolder keyHolder = new GeneratedKeyHolder();
    
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription() != null ? task.getDescription() : "");
            ps.setString(3, task.getPriority());
            ps.setString(4, task.getType());
            ps.setObject(5, task.getDueDate());
            ps.setObject(6, task.getEventDate());
            ps.setObject(7, task.getStartTime());
            ps.setObject(8, task.getEndTime());
            ps.setObject(9, task.getDueTime());
            ps.setString(10, task.getRepeatPattern());
            ps.setObject(11, task.getStartDate());
            ps.setObject(12, task.getEndDate());
            ps.setBoolean(13, task.isCompleted());
            ps.setLong(14, userId);
            return ps;
        }, keyHolder);
    
        if (rowsAffected > 0) {
            task.setId(keyHolder.getKey().longValue());
            saveRepeatDays(task);
            return true;
        }
        return false;
    }    
    
    

    // Save repeating days associated with a task
    private void saveRepeatDays(Task task) {
        if (task.getRepeatDays() != null && isRepeatApplicable(task.getType()) && !task.getRepeatDays().isEmpty()) {
            String insertRepeatDaysSQL = "INSERT INTO repeat_days (task_id, day) VALUES (?, ?)";
            for (String day : task.getRepeatDays()) {
                jdbcTemplate.update(insertRepeatDaysSQL, task.getId(), day.trim());
            }
        }
    }
    
    private boolean isPriorityApplicable(String type) {
        return type.equalsIgnoreCase("homework") || type.equalsIgnoreCase("project") || type.equalsIgnoreCase("test");
    }

    private boolean isRepeatApplicable(String type){
        return type.equalsIgnoreCase("class");
    }

    // Mark a task as completed
    public boolean markAsCompleted(Long taskId) {
        String updateSQL = "UPDATE task SET completed = true, completed_at = NOW() WHERE id = ?";
        return jdbcTemplate.update(updateSQL, taskId) > 0;
    }

    public List<Task> findCompletedByUserId(Long userId) {
        String query = """
            SELECT t.*, GROUP_CONCAT(rd.day) AS repeat_days
            FROM task t
            LEFT JOIN repeat_days rd ON t.id = rd.task_id
            WHERE t.user_id = ? AND t.completed = true
            GROUP BY t.id
        """;
        return jdbcTemplate.query(query, new TaskRowMapper(), userId);
    }

    // Delete a task by ID
    public boolean deleteById(Long taskId) {
        String deleteRepeatDaysSQL = "DELETE FROM repeat_days WHERE task_id = ?";
        String deleteTaskSQL = "DELETE FROM task WHERE id = ?";
    
        jdbcTemplate.update(deleteRepeatDaysSQL, taskId); // Delete repeat days first
        int rowsAffected = jdbcTemplate.update(deleteTaskSQL, taskId); // Delete the task itself
        return rowsAffected > 0;
    }

    public boolean update(Task task) {
        String updateSQL = """
            UPDATE task 
            SET title = ?, description = ?, priority = ?, type = ?, due_date = ?, event_date = ?, 
                start_time = ?, end_time = ?, due_time = ?, repeat_pattern = ?, start_date = ?, 
                end_date = ?, updated_at = NOW() 
            WHERE id = ?
        """;
    
        int rowsAffected = jdbcTemplate.update(updateSQL,
            task.getTitle(),
            task.getDescription() != null ? task.getDescription() : "",
            isPriorityApplicable(task.getType()) ? task.getPriority() : null,
            task.getType(),
            task.getDueDate(),
            task.getEventDate(),
            task.getStartTime(),
            task.getEndTime(),
            task.getDueTime(),
            isRepeatApplicable(task.getType()) ? task.getRepeatPattern() : null,
            task.getStartDate(),
            task.getEndDate(),
            task.getId()
        );
    
        // Update repeat days if applicable
        if (isRepeatApplicable(task.getType())) {
            String deleteRepeatDaysSQL = "DELETE FROM repeat_days WHERE task_id = ?";
            jdbcTemplate.update(deleteRepeatDaysSQL, task.getId());
            saveRepeatDays(task);
        }
    
        return rowsAffected > 0;
    }
    
    
    
}
