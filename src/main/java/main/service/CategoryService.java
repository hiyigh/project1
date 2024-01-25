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

    @Override
    public void add(Category category) {
        categoryRepoMethod.add(category);
    }

    @Override
    public void delete(Long categoryId) {
        categoryRepoMethod.delete(categoryId);
    }

    @Override
    public void update(Category category) {
        categoryRepoMethod.update(category);
    }
    @Override
    public List<Category> getRootCategoryList() {
        return categoryRepoMethod.getRootCategoryList();
    }
    @Override
    public List<Category> getSubCategoryList(Long categoryId) {
        return categoryRepoMethod.getSubCategoryList(categoryId);
    }
}
