package main.controller;

import lombok.RequiredArgsConstructor;
import main.model.Category;
import main.service.LayoutService;
import main.service.method.CategoryMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryMethod categoryMethod;
    private final LayoutService layoutService;
    @GetMapping("edit")
    public String categoryEdit(Model model) {
        layoutService.addLayout(model);
        List<Category> categoryList= categoryMethod.getAllCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "/category/categoryEdit";
    }
}
