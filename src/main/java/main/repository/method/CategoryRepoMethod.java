package main.repository.method;

import main.model.Category;

import java.util.List;

public interface CategoryRepoMethod {
    void add(Category category);
    void delete(Long categoryId);
    void update(Category category);
    List<Category> getAllCategoryList();

    List<Category> getRootCategoryList();

    List<Category> getSubCategoryList(Long categoryId);
}
