package com.CollegeUnify.project.TaskManagement.Task_Mapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.CollegeUnify.project.TaskManagement.Task_Model.Task;

@Component
public class TasksDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch all tasks for calendar display
    public List<Task> getAllTasks() {
        String query = "SELECT id, title, description, priority, due_date, completed, created_at, updated_at, completed_at, event_duration, user_id FROM task";
        return jdbcTemplate.query(query, new TaskRowMapper());
    }

    public List<Task> getCompletedTasks() {
        String query = "SELECT id, title, description, priority, due_date, completed, created_at, updated_at, completed_at, event_duration, user_id FROM task WHERE completed = true";
        return jdbcTemplate.query(query, new TaskRowMapper());
    }

    public Task getTaskById(int taskId) {
        String query = "SELECT id, title, description, priority, due_date, completed, created_at, updated_at, completed_at, event_duration, user_id FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[] { taskId }, new TaskRowMapper());
    }

    public boolean updateTask(Task task) {
        String updateSQL = "UPDATE task SET title = ?, description = ?, priority = ?, type = ?, due_date = ?, completed = ?, updated_at = ?, event_duration = ?, WHERE id = ?";
        int result = jdbcTemplate.update(
            updateSQL, 
            task.getTitle(), 
            task.getDescription(), 
            task.getPriority(), 
            task.getType(),
            task.getDueDate(), 
            task.isCompleted(), 
            task.getUpdatedAt(),
            task.getEventDuration(),
            task.getId()
        );
        return result > 0;
    }

    public boolean addTask(Task task, Long userId) {
        String insertSQL = "INSERT INTO task (title, description, priority, type, due_date, completed, created_at, event_duration, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(
            insertSQL, 
            task.getTitle(), 
            task.getDescription(), 
            task.getPriority(), 
            task.getType(),  // Pass the type
            task.getDueDate(), 
            task.isCompleted(), 
            task.getCreatedAt(), 
            task.getEventDuration(),
            userId
        );
        return result > 0;
    }

    public boolean deleteTask(int taskId) {
        String deleteSQL = "DELETE FROM task WHERE id = ?";
        int result = jdbcTemplate.update(deleteSQL, taskId);
        return result > 0;
    }

    public List<Task> findByUserId(Long userId) {
        String query = "SELECT id, title, description, priority, type, due_date, completed, created_at, updated_at, completed_at, event_duration, user_id FROM task WHERE user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new TaskRowMapper());
    }

    public boolean completeTask(int taskId) {
        String updateSQL = "UPDATE task SET completed = true, completed_at = ? WHERE id = ?";
        int result = jdbcTemplate.update(updateSQL, java.time.LocalDateTime.now(), taskId);
        return result > 0;
    }
}