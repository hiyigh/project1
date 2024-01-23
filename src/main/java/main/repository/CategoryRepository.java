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
    public List<Category> getAllCategoryList() {
        return jdbcTemplate.query("select * from Categories", new BeanPropertyRowMapper<>(Category.class));
    }
}
