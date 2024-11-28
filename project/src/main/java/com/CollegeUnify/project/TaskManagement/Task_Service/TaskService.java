package com.CollegeUnify.project.TaskManagement.Task_Service;

import com.CollegeUnify.project.Application.Application_Model.User;
import com.CollegeUnify.project.Application.Application_Repository.UserRepository;
import com.CollegeUnify.project.TaskManagement.Task_Mapping.TasksDao;
import com.CollegeUnify.project.TaskManagement.Task_Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TasksDao tasksDao;

    // Find all tasks for a specific user
    public List<Task> findByUserId(Long userId) {
        return tasksDao.findByUserId(userId);
    }

    // Find a specific task by ID
    public Task findById(Long taskId) {
        return tasksDao.findById(taskId);
    }

    // Save a new task
    public boolean save(Task task, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        task.setUser(user);
        return tasksDao.save(task, userId);
    }

    // Delete a task by ID
    public boolean deleteById(Long taskId) {
        return tasksDao.deleteById(taskId);
    }

    // Mark a task as completed
    public boolean markAsCompleted(Long taskId) {
        return tasksDao.markAsCompleted(taskId);
    }

    public List<Task> findCompletedTasksByUserId(Long userId) {
        return tasksDao.findCompletedByUserId(userId);
    }

    // Fetch incomplete tasks for a specific user
public List<Task> findIncompleteTasksByUserId(Long userId) {
    return tasksDao.findIncompleteByUserId(userId);
}

// Mark a task as incomplete
public boolean markAsIncomplete(Long taskId) {
    return tasksDao.markAsIncomplete(taskId);
}


    // Validate a task before saving
    public void validateTask(Task task) {
        String type = task.getType();

        if ("homework".equalsIgnoreCase(type) || "project".equalsIgnoreCase(type)) {
            if (task.getDueDate() == null) {
                throw new IllegalArgumentException("Due date is required for Homework and Project tasks.");
            }
        } else if ("personal".equalsIgnoreCase(type) || "test".equalsIgnoreCase(type)) {
            if (task.getEventDate() == null || task.getStartTime() == null || task.getEndTime() == null) {
                throw new IllegalArgumentException("Event date, start time, and end time are required for Personal and Test tasks.");
            }
            if (task.getStartTime() != null && task.getEndTime() != null && !task.getStartTime().isBefore(task.getEndTime())) {
                throw new IllegalArgumentException("Start time must be before end time.");
            }
        } else if ("class".equalsIgnoreCase(type)) {
            if (task.getStartDate() == null || task.getEndDate() == null) {
                throw new IllegalArgumentException("Class start and end dates are required.");
            }
            if (task.getStartDate().isAfter(task.getEndDate())) {
                throw new IllegalArgumentException("Class start date must be before end date.");
            }
        }
    }
}