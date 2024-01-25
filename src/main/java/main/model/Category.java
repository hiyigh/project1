package main.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Category {
    private Long categoryId;
    private Long mainCategoryId;

    private String categoryTitle;
    private int postCount;
    private boolean isRoot;
    private Category (Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryTitle = category.getCategoryTitle();
        this.postCount = category.getPostCount();
        this.isRoot = category.isRoot();
        this.mainCategoryId = category.getMainCategoryId();
    }
}
