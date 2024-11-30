package com.CollegeUnify.project.Application.Application_Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.CollegeUnify.project.Application.Application_Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    
    @Autowired
    @Lazy
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/about/**").permitAll()
                .requestMatchers("/features/**").permitAll()
                .requestMatchers("/pricing-coming-soon/**").permitAll()
                .requestMatchers("/resources-coming-soon/**").permitAll()
                .requestMatchers("/contact-coming-soon/**").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/registration**").permitAll()  
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/img/**").permitAll() 
                .requestMatchers("/images/**").permitAll() //permitAll pages, anyone can access
                .requestMatchers("/tasks/**").authenticated()
                .requestMatchers("/tasks/add/**").authenticated()
                .requestMatchers("/dashboard/**").authenticated()
                .requestMatchers("/my-resources-coming-soon/**").authenticated()
                .requestMatchers("/settings-coming-soon/**").authenticated()
                .requestMatchers("/chat-coming-soon/**").authenticated() //authenticated pages, only logged in users can access

                .anyRequest().authenticated()
                )
            .formLogin((form) -> form
                .loginPage("/login")   //override default login page to be /login
                .defaultSuccessUrl("/dashboard", true) // Redirect to a user's dashboard after successful login
                .permitAll()) //anyone can access login page
            .logout((logout) -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/") //when logout successful, redirect to home page
                .permitAll());

                return http.build();

    }

}