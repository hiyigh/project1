package main.dto;

import main.model.Category;

import java.util.List;

public class CategoryDto {
    private Long categoryId;
    private String categoryTitle;
    private Long mainCategoryId;
    private int postCount;
    private List<Category> subCategoryList;
}
