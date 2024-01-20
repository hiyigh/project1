package main.repository;

import lombok.RequiredArgsConstructor;
import main.model.User;
import main.service.method.UserMethod;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class UserRepository implements UserMethod {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public User getUserById(Long userId) {
        return jdbcTemplate.queryForObject("select * from Users where userId = ?", new Object[]{userId},
                new BeanPropertyRowMapper<>(User.class));
    }
    @Override
    public User getUserByNameOrNull(String userName) {
        return jdbcTemplate.queryForObject("select * from Users where userName like ?", new Object[]{"%" + userName + "%"},
                new BeanPropertyRowMapper<>(User.class));
    }
    @Override
    public User getUserByEmailOrNull(String userEmail) {
        return jdbcTemplate.queryForObject("select * from Users where userEmail like ?", new Object[]{"%"+ userEmail + "%"},
                new BeanPropertyRowMapper<>(User.class));
    }
    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from Users", new BeanPropertyRowMapper<>(User.class));
    }
}
