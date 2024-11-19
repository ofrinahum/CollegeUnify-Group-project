package com.CollegeUnify.project.TaskManagement.Task_Mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import com.CollegeUnify.project.TaskManagement.Task_Model.Task;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
    Task task = new Task();

    // Map basic fields
    task.setId(rs.getLong("id"));
    task.setTitle(rs.getString("title"));
    task.setDescription(rs.getString("description"));
    task.setPriority(rs.getString("priority"));
    task.setType(rs.getString("type"));
    task.setEventDuration(rs.getLong("event_duration"));

    // Map date_time (either due_date or event_date_time) based on the task type
    LocalDateTime dateTime = null;
    String type = task.getType();  

    if ("personal".equals(type) || "test".equals(type)) {
        // For 'personal' or 'test' types, map event_date_time
        java.sql.Timestamp eventDateTimeTimestamp = rs.getTimestamp("event_date_time");
        if (eventDateTimeTimestamp != null) {
            dateTime = eventDateTimeTimestamp.toLocalDateTime();
            task.setEventDateTime(dateTime);  // Set event date time
        }
    } else {
        // For other types (project, homework), map due_date
        java.sql.Timestamp dueDateTimestamp = rs.getTimestamp("due_date");
        if (dueDateTimestamp != null) {
            dateTime = dueDateTimestamp.toLocalDateTime();
            task.setDueDate(dateTime);  // Set due date
        }
    }

    // Map other timestamps
    java.sql.Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
    if (createdAtTimestamp != null) {
        task.setCreatedAt(createdAtTimestamp.toLocalDateTime());
    }

    java.sql.Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
    if (updatedAtTimestamp != null) {
        task.setUpdatedAt(updatedAtTimestamp.toLocalDateTime());
    }

    java.sql.Timestamp completedAtTimestamp = rs.getTimestamp("completed_at");
    if (completedAtTimestamp != null) {
        task.setCompletedAt(completedAtTimestamp.toLocalDateTime());
    }

    // Map completion status
    task.setCompleted(rs.getBoolean("completed"));

    return task;
}
}
