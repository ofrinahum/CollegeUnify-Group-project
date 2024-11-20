package com.CollegeUnify.project.Application.Application_Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//THIS CODE CONTROLS MAIN PAGES

@Controller
public class MainController {
    
    @GetMapping("/login")   
    public String login(){
        return "login";
    }

    @RequestMapping("/")
    public String home(){
        return "unauth_homepage";
    }

    @RequestMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
}
