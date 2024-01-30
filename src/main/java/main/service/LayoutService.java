package main.service;

import lombok.RequiredArgsConstructor;
import main.dto.PrincipalDetails;
import main.model.Category;
import main.model.User;
import main.service.method.CategoryMethod;
import main.service.method.PostMethod;
import main.service.method.UserMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LayoutService {
    private final CategoryMethod categoryMethod;
    private final UserMethod userMethod;

    public void addLayout(Model model, Authentication authentication) {
        List<Category> categoryList = categoryMethod.getAllCategoryList();
        String userRole = "default";
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            User user = userMethod.getUserByEmailOrNull(principalDetails.getUsername());
            int roleLen = user.getRole().toString().length();
            userRole = user.getRole().toString().substring(1, roleLen - 1);
        }
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("userRole", userRole);
    }
}
