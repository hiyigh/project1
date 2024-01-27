package main.configure;

import main.service.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/img/**", "/layout/layout", "/layout/sidebar", "/post/postView",
                        "/post/postList", "/shop/itemDetail", "/shop/shopList", "/aboutMe", "/home", "/login", "/enroll").permitAll()
                    .antMatchers("/post/postEdit", "/post/postWrite", "/shop/itemBasket", "/shop/itemBuy").hasRole("USER")
                    .antMatchers("/category/categoryEdit").hasRole("ADMIN")

                .and()

                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("userEmail")
                    .passwordParameter("userPassword")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login")
                    .permitAll()
                    .and()

                .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login")
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);

        http    .logout()
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/");
    }
}
