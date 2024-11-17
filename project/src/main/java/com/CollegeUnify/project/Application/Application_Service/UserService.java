package com.CollegeUnify.project.Application.Application_Service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.CollegeUnify.project.Application.Application_Model.User;
import com.CollegeUnify.project.Application.Application_Web_Dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
    User save(UserRegistrationDto registrationDto);
}
