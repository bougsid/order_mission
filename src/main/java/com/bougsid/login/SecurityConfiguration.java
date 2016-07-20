package com.bougsid.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by ayoub on 6/26/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    EmployeUserDetailsService employeUserDetailsService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeUserDetailsService);
    }

    @Autowired
    private AuthHandler authHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/home").permitAll()
//                .antMatchers("/admin/**").access("hasRole('DE')")
                .antMatchers("/employes").hasRole("ADMIN")
                .antMatchers("/banks").hasRole("ADMIN")
                .antMatchers("/services").hasRole("ADMIN")
                .antMatchers("/types").hasRole("ADMIN")
                .antMatchers("/taux").hasRole("ADMIN")
                .antMatchers("/addmission").hasRole("USER")
                .antMatchers("/missions").authenticated()
                .and().formLogin().loginPage("/login").successHandler(authHandler)
                .usernameParameter("username").passwordParameter("password")
                .and().exceptionHandling().accessDeniedPage("/404");
    }
}
