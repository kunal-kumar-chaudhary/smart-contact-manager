package com.scm.config;

import com.scm.services.impl.SecurityCustomClassDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfiig {

//    // user create and login using java code with in memory service
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.
//                withDefaultPasswordEncoder().
//                username("kunal").
//                password("kunal").
//                roles("USER", "ADMIN").
//                build();
//
//        UserDetails user2 = User.
//                withDefaultPasswordEncoder().
//                username("john").
//                password("john").
//                build();
//
//        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);
//        return inMemoryUserDetailsManager;
//    }


    // above was hard coded method.
    // below is the method to create user and login using database
    private final SecurityCustomClassDetailService userDetailsService;
    public SecurityConfiig (SecurityCustomClassDetailService securityCustomClassDetailService) {
        this.userDetailsService = securityCustomClassDetailService;
    }

    // configuration of authentication provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider() {

        // daoAuthenticationProvider has all the methods to authenticate the user
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // configuration of security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> {
//            authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        /*
        Solution: Use failureUrl() instead of failureForwardUrl()
        failureUrl("/login?error=true"): This tells Spring Security to redirect to /login?error=true,
        meaning it will send a new GET request to the login page.
        The login page can now handle this request as expected.
         */

        // form default login page
        httpSecurity.formLogin(formLogin ->{
            formLogin.loginPage("/login"); // login page
            formLogin.loginProcessingUrl("/authenticate"); // login processing url
            formLogin.defaultSuccessUrl("/user/dashboard", true); // once authenticated, where to go -> without true, The redirect to /user/profile?continue happened because Spring Security was trying to send the user back to the page they originally requested before logging in.
            formLogin.failureUrl("/login?error=true"); // if login fails, where to go
            formLogin.usernameParameter("email"); // username parameter
            formLogin.passwordParameter("password"); // password parameter


//            formLogin.failureHandler(new AuthenticationFailureHandler() {
//                @Override
//                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                    throw new UnsupportedOperationException("unimplemented methods");
//                }
//            });
//            formLogin.successHandler(new AuthenticationSuccessHandler() {
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                    throw new UnsupportedOperationException("unimplemented methods");
//                }
//            });



        });


        // disabling csrf
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // configuring logout page
        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
