package main.service;

import lombok.RequiredArgsConstructor;
import main.model.Category;
import main.service.method.CategoryMethod;
import main.service.method.PostMethod;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LayoutService {
    private final CategoryMethod categoryMethod;
    public void addLayout(Model model) {
        List<Category> categoryList = categoryMethod.getAllCategoryList();
        for (int i = 0; i<categoryList.size(); ++i) {
            System.out.println("categoryList: " + categoryList.get(i).getCategoryTitle() + categoryList.size()
                    + categoryList.get(i).getCategoryId()+ categoryList.get(i).getMainCategoryId());
        }

        model.addAttribute("categoryList",categoryList);
    }
}
