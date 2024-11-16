package com.CollegeUnify.project.Application_Web_Dto;

public class UserRegistrationDto {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //DEFAULT CONSTRUCTOR

    public UserRegistrationDto(){

    }

    //CONSTRUCTOR

    public UserRegistrationDto(String firstName, String lastName, String email, String password){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //GET AND SET METHODS FOR EACH PRIVATE VARIABLE

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
