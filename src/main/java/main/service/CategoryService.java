package main.service;

import lombok.RequiredArgsConstructor;
import main.model.Category;
import main.repository.method.CategoryRepoMethod;
import main.service.method.CategoryMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryMethod {
    private final CategoryRepoMethod categoryRepoMethod;
    @Override
    public List<Category> getAllCategoryList() {
        return categoryRepoMethod.getAllCategoryList();
    }
}
