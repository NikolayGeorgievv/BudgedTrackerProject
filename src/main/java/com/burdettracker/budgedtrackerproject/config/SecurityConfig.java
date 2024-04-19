package com.burdettracker.budgedtrackerproject.config;

import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.user.UserDetailImpl;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                // Allow all static resources to be available.
                                .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())).permitAll()
                                .requestMatchers("/images/**").permitAll()
//                                // Allow anyone to see the home page, the registration page and the login form.
                                .requestMatchers("/homePage", "/users/login", "/users/register", "/").permitAll()
                                //Allowing all, just for testing!!
//                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(
                        formLogin -> {
                            formLogin
                                    // redirect here when we access something which is not allowed.
                                    // also this is the page where we perform login.
                                    .loginPage("/users/login")
                                    // The names of the input fields (in our case in login.html)
                                    .usernameParameter("email")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/index")
                                    .failureForwardUrl("/users/login-fail");
                        }
                ).logout(
                        logout -> {
                            logout
                                    .logoutUrl("/logout")
                                    .logoutSuccessUrl("/homePage")
                                    .invalidateHttpSession(true);
                        }
//        ).rememberMe(
//                rememberMe ->
//                        rememberMe
//                                .key(rememberMeKey)
//                                .rememberMeParameter("rememberme")
//                                .rememberMeCookieName("rememberme")
//        ).oauth2Login(
//                oauth -> oauth.successHandler(oAuthSuccessHandler)
                ).csrf(AbstractHttpConfigurer::disable)
                .build();


    }

    @Bean
    public UserDetailImpl userDetailsService(UserRepository userRepository) {
        return new UserDetailImpl(userRepository);
    }

}
