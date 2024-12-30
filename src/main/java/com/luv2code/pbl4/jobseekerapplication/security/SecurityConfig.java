package com.luv2code.pbl4.jobseekerapplication.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, active from user where email = ?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select u.email, r.role " +
                        "from user u " +
                        "inner join roles r on u.user_id = r.user_id " +
                        "where u.email = ?"
        );




        return jdbcUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Chỉ cho phép ADMIN vào
                        .requestMatchers("/user/profile").authenticated() // Bắt buộc đăng nhập
                        .requestMatchers("/**").permitAll()
                )
            .formLogin(form ->
                    form
                            .loginPage("/dang-nhap")
                            .loginProcessingUrl("/authenticateTheUser")
                            .permitAll()
            )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/viec-lam")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(configure -> configure.accessDeniedPage("/access-denied"))
        ;

        return http.build();
    }
}
