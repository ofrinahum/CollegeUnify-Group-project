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

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

    @RequestMapping("/features")
    public String features(){
        return "features";
    }

    @RequestMapping("/contact-coming-soon")
    public String contact(){
        return "coming_soon";
    }

    @RequestMapping("/resources-coming-soon")
    public String resources(){
        return "coming_soon";
    }

    @RequestMapping("/pricing-coming-soon")
    public String pricing(){
        return "coming_soon";
    }

    @RequestMapping("/chat-coming-soon")
    public String chat(){
        return "coming_soon_auth";
    }

    @RequestMapping("/my-resources-coming-soon")
    public String myResources(){
        return "coming_soon_auth";
    }

    @RequestMapping("/settings-coming-soon")
    public String settings(){
        return "coming_soon_auth";
    }
}
