package com.CollegeUnify.project.TaskManagement.Task_Web;

import com.CollegeUnify.project.Application.Application_Model.User;
import com.CollegeUnify.project.Application.Application_Repository.UserRepository;
import com.CollegeUnify.project.Application.Application_Service.UserService;
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
    private UserRepository userRepository;  // Injecting UserService for accessing User data

    // Get all tasks and render the task management page
    @GetMapping
    public String showTaskPage(Model model, Principal principal) {
    String username = principal.getName(); // Get the authenticated user's username (email)
    User user = userRepository.findByEmail(username);

    if (user == null) {
        throw new IllegalStateException("Authenticated user not found in the database");
    }

    List<Task> tasks = taskService.getTasksByUserId(user.getId()); // Fetch tasks for the logged-in user
    model.addAttribute("tasks", tasks); // Add tasks to model
    model.addAttribute("user", user); // Add user to model
    return "task_management"; // Return the task management page
}

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, Principal principal) {
    String username = principal.getName();
    User user = userRepository.findByEmail(username);

    if (user == null) {
        throw new IllegalStateException("Authenticated user not found in the database");
    }

    // Log the received task
    System.out.println("Received Task: " + task);

    // Set the user and save the task
    task.setUser(user);
    taskService.addTask(task, user.getId());

    return "redirect:/tasks";
}


}