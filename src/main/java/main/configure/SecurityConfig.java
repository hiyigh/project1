package main.configure;

import lombok.RequiredArgsConstructor;
import main.service.PrincipalOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/*",
                "/img/*", "/js/*");
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/chat","/chat/*","/comment/*","/post/write","/post/edit",
                                "post/delete","/shop/order","/shop/basket","/shop/payment").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/category/edit","/shop/add","/shop/delete").hasRole("ADMIN")
                        .anyRequest().permitAll())
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
                .logout(logout -> logout.logoutUrl("/logout")
                        .deleteCookies("remember-me")
                        .logoutSuccessUrl("/"));
        return http.build();
    }
}