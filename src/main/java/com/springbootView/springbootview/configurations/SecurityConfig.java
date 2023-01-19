package com.springbootView.springbootview.configurations;

import com.springbootView.springbootview.services.JpaUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JpaUserDetailService detailService;

    public SecurityConfig(JpaUserDetailService jpaUserDetailService) {
        this.detailService = jpaUserDetailService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(auth -> {
                    auth .antMatchers( "/", "/about", "/products").permitAll()
                            .antMatchers( "/css/**", "/img/**", "/fonts/**", "/javascript/**").permitAll()
                            .antMatchers("/order").hasAuthority("USER")
                            .antMatchers("/admin/**").hasAnyAuthority("ADMIN", "USER")
                            .anyRequest().authenticated();
                })
                .userDetailsService(detailService)
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout");


        return http.build();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
