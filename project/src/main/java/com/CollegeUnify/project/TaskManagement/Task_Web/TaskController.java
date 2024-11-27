package com.CollegeUnify.project.TaskManagement.Task_Web;

import com.CollegeUnify.project.Application.Application_Model.User;
import com.CollegeUnify.project.Application.Application_Repository.UserRepository;
import com.CollegeUnify.project.TaskManagement.Task_Mapping.TasksDao;
import com.CollegeUnify.project.TaskManagement.Task_Model.Task;
import com.CollegeUnify.project.TaskManagement.Task_Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private TasksDao tasksDao;

    // Show task management page
    @GetMapping
    public String showTaskPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new IllegalStateException("Authenticated user not found in the database.");
        }

        List<Task> tasks = taskService.findByUserId(user.getId());
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        return "task_management"; // Refers to task_management.html
    }

    // Add a new task
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, Principal principal) {
        System.out.println("Task received: " + task); // Debug log
        String username = principal.getName();
        User user = userRepository.findByEmail(username);
    
        if (user == null) {
            throw new IllegalStateException("Authenticated user not found.");
        }
    
        taskService.save(task, user.getId());
        return "redirect:/tasks";
    }
    
    // Mark a task as completed
    @PostMapping("/{taskId}/complete")
    public String completeTask(@PathVariable Long taskId) {
        taskService.markAsCompleted(taskId);
        return "redirect:/tasks";
    }

    // Delete a task
    @PostMapping("/{taskId}/delete")
public String deleteTask(@PathVariable Long taskId) {
    taskService.deleteById(taskId);
    return "redirect:/tasks";
}

@GetMapping("/api/{taskId}")
@ResponseBody
public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
    Task task = tasksDao.findById(taskId);
    if (task == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(task);
}

@PutMapping("/{taskId}/update")
@ResponseBody
public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
    Task existingTask = tasksDao.findById(taskId);
    if (existingTask == null) {
        return ResponseEntity.notFound().build();
    }

    updatedTask.setId(taskId);
    boolean success = tasksDao.update(updatedTask);
    if (success) {
        return ResponseEntity.ok("Task updated successfully");
    } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update task");
    }
}


    // Fetch tasks as JSON for the calendar
    @GetMapping("/api")
    @ResponseBody
    public List<Task> getTasksForUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new IllegalStateException("Authenticated user not found in the database.");
        }

        return taskService.findByUserId(user.getId());
    }
}
