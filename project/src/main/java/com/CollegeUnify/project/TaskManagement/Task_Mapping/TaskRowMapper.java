package com.CollegeUnify.project.TaskManagement.Task_Mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.CollegeUnify.project.TaskManagement.Task_Model.Task;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
    
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setPriority(rs.getString("priority"));
        task.setType(rs.getString("type"));
    
        task.setDueDate(rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null);
        task.setDueTime(rs.getTime("due_time") != null ? rs.getTime("due_time").toLocalTime() : null);
    
        task.setEventDate(rs.getDate("event_date") != null ? rs.getDate("event_date").toLocalDate() : null);
        task.setStartTime(rs.getTime("start_time") != null ? rs.getTime("start_time").toLocalTime() : null);
        task.setEndTime(rs.getTime("end_time") != null ? rs.getTime("end_time").toLocalTime() : null);
    
        task.setRepeatPattern(rs.getString("repeat_pattern"));
        task.setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
        task.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
    
        task.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
        task.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        task.setCompletedAt(rs.getTimestamp("completed_at") != null ? rs.getTimestamp("completed_at").toLocalDateTime() : null);
    
        String repeatDays = rs.getString("repeat_days");
        if (repeatDays != null && !repeatDays.isEmpty()) {
            task.setRepeatDays(List.of(repeatDays.split(",")));
        }
    
        return task;
    }
}
    