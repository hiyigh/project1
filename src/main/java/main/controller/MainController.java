package main.controller;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@NoArgsConstructor
public class MainController {
    @GetMapping("/")
    public String home() {
        return "/home";
    }
    @GetMapping("/aboutMe")
    public String aboutMe() {
        return "/aboutMe";
    }
}
