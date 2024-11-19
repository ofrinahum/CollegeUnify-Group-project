package com.CollegeUnify.project.TaskManagement.Task_Model;

import java.time.LocalDateTime;
import java.time.Duration;
import com.CollegeUnify.project.Application.Application_Model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Task name
    private String description;
    private String priority; // e.g., "low", "medium", "high"

    @Column(name = "due_date")
    private LocalDateTime dueDate; // Used for "homework" and "project"

    @Column(name = "event_date_time")
    private LocalDateTime eventDateTime; // Used for "personal" or "test" tasks

    private String type; // Can be "test", "homework", "project", or "personal"

    private boolean completed;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "event_duration")
    private Long duration; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor
    public Task() {
    }

    // Constructor
    public Task(Long id, String title, String description, String priority, LocalDateTime dueDate, 
                LocalDateTime eventDateTime, String type, boolean completed, LocalDateTime createdAt, 
                LocalDateTime updatedAt, LocalDateTime completedAt, User user, Long duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.eventDateTime = eventDateTime;
        this.type = type;
        this.completed = completed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.user = user;
        this.duration = duration;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getEventDuration() {
        return duration;
    }

    public void setEventDuration(Long eventDuration) {
        this.duration = eventDuration;
    }

    // Convenience method for marking a task as completed
    public void markAsCompleted() {
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }
}
