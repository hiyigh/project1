package main.configure;

import lombok.RequiredArgsConstructor;
import main.service.PrincipalDetailsService;
import main.service.PrincipalOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(c->c.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/css/**", "/js/**", "/img/**", "/layout/layout", "/layout/sidebar", "/post/postView",
                        "/post/postList", "/shop/itemDetail", "/shop/shopList", "/aboutMe", "/home", "/login", "/enroll").permitAll()
                        .requestMatchers("/post/postEdit", "/post/postWrite", "/shop/itemBasket", "/shop/itemBuy").hasRole("USER")
                        .requestMatchers("/category/categoryEdit").hasRole("ADMIN"))
                .formLogin(login -> login.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("userEmail")
                        .passwordParameter("userPassword")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login")
                        .permitAll())
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login")
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(principalOauth2UserService)))
                .logout(logout->logout.logoutUrl("/logout")
                        .deleteCookies("remember-me")
                        .logoutSuccessUrl("/"));
        return http.build();
    }
}