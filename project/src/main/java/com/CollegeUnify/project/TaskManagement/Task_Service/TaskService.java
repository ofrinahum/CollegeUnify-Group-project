package com.CollegeUnify.project.TaskManagement.Task_Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CollegeUnify.project.Application.Application_Model.User;
import com.CollegeUnify.project.Application.Application_Repository.UserRepository;
import com.CollegeUnify.project.TaskManagement.Task_Mapping.TasksDao;
import com.CollegeUnify.project.TaskManagement.Task_Model.Task;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TasksDao tasksDao;

    public boolean addTask(Task task, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        task.setUser(user);  // Set the actual User object
        return tasksDao.addTask(task, userId);
    }

    // Update an existing task
    public boolean updateTask(Task task) {
        return tasksDao.updateTask(task);
    }

    // Delete a task by ID
    public boolean deleteTask(int taskId) {
        return tasksDao.deleteTask(taskId);
    }

    // Fetch a task by ID
    public Task getTaskById(int taskId) {
        return tasksDao.getTaskById(taskId);
    }

    // Mark a task as completed
    public boolean completeTask(int taskId) {
        return tasksDao.completeTask(taskId);
    }

    // Fetch all completed tasks
    public List<Task> getCompletedTasks() {
        return tasksDao.getCompletedTasks();
    }

    // **New method to get tasks by userId**
    public List<Task> getTasksByUserId(Long userId) {
        return tasksDao.findByUserId(userId);  // This assumes your DAO supports this method
    }
}