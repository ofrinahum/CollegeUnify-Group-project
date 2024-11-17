package com.CollegeUnify.project.Application.Application_Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.CollegeUnify.project.Application.Application_Service.UserService;
import com.CollegeUnify.project.Application.Application_Web_Dto.UserRegistrationDto;

//THIS CODE CONTROLS REGISTRATION PAGES

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    

    private UserService userService;

    //CONSTRUCTOR

    public UserRegistrationController(UserService userService){
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "registration.html";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto){
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
