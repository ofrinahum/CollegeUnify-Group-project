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

//DON'T CHANGE ANYTHING HERE UNLESS YOU HAVE A REASON TO, THIS CODE CONFIGURES SECURITY SETTINGS

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
                .requestMatchers("/tasks/**").authenticated()
                .requestMatchers("/registration**").permitAll()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/img/**").permitAll() // Allow /img/** for images
                .requestMatchers("/images/**").permitAll() // Add this line
                .requestMatchers("/dashboard/**").authenticated()
                .requestMatchers("/about/**").permitAll()
                .requestMatchers("/features/**").permitAll()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                .permitAll())
            .logout((logout) -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
                .permitAll());

                return http.build();


    }

}