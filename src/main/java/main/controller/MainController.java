package main.controller;

import ch.qos.logback.core.Layout;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import main.service.LayoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final LayoutService layoutService;
    @GetMapping("/")
    public String home(Model model) {
        layoutService.addLayout(model);
        return "/home";
    }
    @GetMapping("/aboutMe")
    public String aboutMe(Model model) {
        layoutService.addLayout(model);
        return "/aboutMe";
    }
}
