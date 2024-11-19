package com.CollegeUnify.project.Application.Application_Model;

import java.util.ArrayList;
import java.util.Collection;

import com.CollegeUnify.project.TaskManagement.Task_Model.Task;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "first_name")
private String firstName;

@Column(name = "last_name")
private String lastName;

private String email;

private String password;

@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
@JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(
        name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "id"))

private Collection<Role> roles;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)

private Collection<Task> tasks = new ArrayList<>();
//ONETOMANY TO HERE IS ALL FROM RECENT COMMIT, IF ISSUES ARE CAUSED DELETE

    //DEFAULT CONSTRUCTOR
    public User(){

    }

    //CONSTRUCTOR 
    public User(String firstName, String lastName, String email, String password, Collection<Role> roles)
    {   super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    //GET AND SET METHODS FOR EACH PRIVATE VARIABLE

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

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

    public void setRoles(Collection<Role> roles){
        this.roles = roles;
    }

    public Collection<Role> getRoles(){
        return roles;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }
    //SET AND GET TASKS METHODS ARE ADDED IN MOST RECENT COMMIT PLEASE DELETE IF CAUSES ISSUES
    
}
