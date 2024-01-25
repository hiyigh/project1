package main.repository;

import lombok.RequiredArgsConstructor;
import main.model.Category;
import main.repository.method.CategoryRepoMethod;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository implements CategoryRepoMethod {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void add(Category category) {
        jdbcTemplate.update("insert into Categories(categoryId, mainCategoryId, categoryTitle, postCount, isRoot) "
                        ,category.getCategoryId(),category.getMainCategoryId(),category.getCategoryTitle(),category.getPostCount(),category.isRoot());
    }

    @Override
    public void delete(Long categoryId) {
        jdbcTemplate.update("delete from Categories where categoryId = ?", categoryId);
    }

    @Override
    public void update(Category category) {
        jdbcTemplate.update("update Categories set categoryTitle = ?, isRoot = ?, mainCategoryId = ?",
                category.getCategoryTitle(), category.isRoot(),category.getMainCategoryId());
    }

    @Override
    public List<Category> getAllCategoryList() {
        List<Category> categoryList = jdbcTemplate.query("select * from Categories", new BeanPropertyRowMapper<>(Category.class));

        return categoryList;
    }

    @Override
    public List<Category> getRootCategoryList() {
        return jdbcTemplate.query("select * from Categories where isRoot = 1", new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public List<Category> getSubCategoryList(Long categoryId) {
        return jdbcTemplate.query("select * from Categories where mainCategoryId = ?", new Object[]{categoryId},
                new BeanPropertyRowMapper<>(Category.class));
    }
}
