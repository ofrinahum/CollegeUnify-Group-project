package com.CollegeUnify.project.TaskManagement.Task_Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    @Column(name = "priority")
    private String priority; // "low", "medium", "high"

    @Column(nullable = false)
    private String type; // "homework", "project", "personal", "test", "class"

    // Homework and Project
    @Column(name = "due_date")
    private LocalDate dueDate;

    // Personal and Test
    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "due_time")
    private LocalTime dueTime;

    @Column(name = "repeat_pattern")
    private String repeatPattern; // "DAILY", "WEEKLY", "MONTHLY"

    // Class-specific fields
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "repeat_days", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "day")
    private List<String> repeatDays;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // Default constructor
    public Task() {}

    // Constructor
    public Task(Long id, String title, String description, String priority, String type, LocalDate dueDate,
                LocalDate eventDate, LocalTime startTime, LocalTime endTime, boolean isRepeating,
                String repeatPattern, LocalDate classStartDate, LocalDate classEndDate, boolean completed,
                User user, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime completedAt,
                List<String> repeatDays) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.type = type;
        this.dueDate = dueDate;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatPattern = repeatPattern;
        this.startDate = classStartDate;
        this.endDate = classEndDate;
        this.completed = completed;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completedAt = completedAt;
        this.repeatDays = repeatDays; // Initialize repeatDays
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setDueTime(LocalTime dueTime){
        this.dueTime = dueTime;
    }

    public LocalTime getDueTime(){
        return dueTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRepeatPattern() {
        return repeatPattern;
    }

    public void setRepeatPattern(String repeatPattern) {
        this.repeatPattern = repeatPattern;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate classStartDate) {
        this.startDate = classStartDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate classEndDate) {
        this.endDate = classEndDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    // Priority-based duration logic
    public int getPriorityDuration() {
        if (this.priority == null || this.priority.isEmpty()) {
            // Return a default value or handle the null case based on your logic
            return 0; // Default duration for tasks with no priority
        }
        switch (this.priority.toLowerCase()) {
            case "low": return 5;
            case "medium": return 10;
            case "high": return 15;
            default: throw new IllegalArgumentException("Unknown priority: " + this.priority);
        }
    }

    // Convenience method for marking a task as completed
    public void markAsCompleted() {
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }

    public List<String> getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(List<String> repeatDays) {
        this.repeatDays = repeatDays;
    }
}
