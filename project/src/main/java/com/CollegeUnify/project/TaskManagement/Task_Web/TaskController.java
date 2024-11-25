package com.CollegeUnify.project.TaskManagement.Task_Web;

import com.CollegeUnify.project.Application.Application_Model.User;
import com.CollegeUnify.project.Application.Application_Repository.UserRepository;
import com.CollegeUnify.project.TaskManagement.Task_Model.Task;
import com.CollegeUnify.project.TaskManagement.Task_Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String username = principal.getName();
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new IllegalStateException("Authenticated user not found in the database.");
        }

        taskService.validateTask(task); // Validate based on type
        taskService.save(task, user.getId()); // Save the task
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
