package com.CollegeUnify.project.Application_Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//THIS CODE CONTROLS MAIN PAGES

@Controller
public class MainController {
    
    @GetMapping("/login")   
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }
}
