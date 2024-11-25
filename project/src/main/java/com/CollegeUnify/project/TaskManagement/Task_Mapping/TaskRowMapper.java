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

        // Map basic fields
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setPriority(rs.getString("priority"));
        task.setType(rs.getString("type"));

        // Map date and time fields
        java.sql.Date dueDate = rs.getDate("due_date");
        if (dueDate != null) {
            task.setDueDate(dueDate.toLocalDate());
        }

        java.sql.Date eventDate = rs.getDate("event_date");
        if (eventDate != null) {
            task.setEventDate(eventDate.toLocalDate());
        }

        java.sql.Time startTime = rs.getTime("start_time");
        if (startTime != null) {
            task.setStartTime(startTime.toLocalTime());
        }

        java.sql.Time endTime = rs.getTime("end_time");
        if (endTime != null) {
            task.setEndTime(endTime.toLocalTime());
        }

        java.sql.Time dueTime = rs.getTime("due_time");
        if (dueTime != null) {
            task.setDueTime(dueTime.toLocalTime());
        }

        // Map repeating and class-related fields
        task.setRepeating(rs.getBoolean("is_repeating"));
        task.setRepeatPattern(rs.getString("repeat_pattern"));

        java.sql.Date classStartDate = rs.getDate("start_date");
        if (classStartDate != null) {
            task.setStartDate(classStartDate.toLocalDate());
        }

        java.sql.Date classEndDate = rs.getDate("end_date");
        if (classEndDate != null) {
            task.setEndDate(classEndDate.toLocalDate());
        }

        // Map timestamps
        java.sql.Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            task.setCreatedAt(createdAt.toLocalDateTime());
        }

        java.sql.Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            task.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        java.sql.Timestamp completedAt = rs.getTimestamp("completed_at");
        if (completedAt != null) {
            task.setCompletedAt(completedAt.toLocalDateTime());
        }

        // Map completion status
        task.setCompleted(rs.getBoolean("completed"));

        // Map repeat days (they are fetched in the SQL query via LEFT JOIN)
        List<String> repeatDaysList = new ArrayList<>();
        
        // Check if the result set has a repeat day value
        String repeatDay = rs.getString("day");  // "day" column from the repeat_days table
        if (repeatDay != null && !repeatDay.isEmpty()) {
            repeatDaysList.add(repeatDay);
        }

        // If the task has any repeat days, we set them
        if (!repeatDaysList.isEmpty()) {
            task.setRepeatDays(repeatDaysList);
        }

        return task;
    }
}
