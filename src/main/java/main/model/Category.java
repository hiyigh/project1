package main.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Category {
    private Long categoryId;
    private Long mainCategoryId;

    private String categoryTitle;
    private int postCount;
    private boolean isRoot;
    private List<Category> subCategoryList;
    private Category (Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryTitle = category.getCategoryTitle();
        this.postCount = category.getPostCount();
        this.isRoot = category.isRoot();
        this.mainCategoryId = category.getMainCategoryId();
        this.subCategoryList = category.getSubCategoryList();
    }

    public static void recursiveCategory(List<Category> source, Category root, int categorySize) {
        if (categorySize == 0) return;

        Category category = source.get(0);
        if (category.isRoot) {
            root.subCategoryList.add(new Category(category));
            source.remove(category);

            int idx = root.subCategoryList.size() - 1;
            recursiveCategory(source, root.subCategoryList.get(idx), categorySize - 1);
        } else {
            for (int j = source.size() - 1; j >= 0; --j) {
                if (root.getCategoryId().equals(source.get(j).getMainCategoryId())) {
                    Category subCategory = source.get(j);
                    root.subCategoryList.add(new Category(subCategory));
                    source.remove(subCategory);

                    int idx = root.subCategoryList.size() - 1;
                    recursiveCategory(source, root.subCategoryList.get(idx), categorySize - 1);
                }
            }
        }
    }
}
