package main.repository;

import lombok.RequiredArgsConstructor;
import main.model.enumeration.AddOrDelete;
import main.model.enumeration.HistoryType;
import main.model.User;
import main.repository.method.UserRepoMethod;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class UserRepository implements UserRepoMethod {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(User user) {
        jdbcTemplate.update("insert into Users (userName, userEmail, userPassword, userSex, role, createdTime, basket, commentHistory, postHistory) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUserName(), user.getUserEmail(), user.getUserPassword(), user.isUserSex(), user.getRole().toString(),
                user.getCreatedTime(), user.getBasket(), user.getCommentHistory(), user.getPostHistory());
    }


    @Override
    public User find(Long userId) {
        return jdbcTemplate.queryForObject("select * from Users where userId = ?", new Object[]{userId},
                new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getUserById(Long userId) {
        try {
            User user = jdbcTemplate.queryForObject("select * from Users where userId = ?", new Object[]{userId},
                    new BeanPropertyRowMapper<>(User.class));
            return user;
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public User getUserByNameOrNull(String userName) {
        return jdbcTemplate.queryForObject("select * from Users where userName like ?", new Object[]{"%" + userName + "%"},
                new BeanPropertyRowMapper<>(User.class));
    }
    @Override
    public User getUserByEmailOrNull(String userEmail) {
        User user = new User();
        try {
            return user = jdbcTemplate.queryForObject("select * from Users where userEmail = ?", new Object[]{userEmail},
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from Users", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void setUserHistory(Long userId, Integer newData, HistoryType type, AddOrDelete addOrDelete) {
        String element = "";
        switch(type) {
            case COMMENT:
                element = "commentHistory";
                break;
            case POST:
                element = "postHistory";
                break;
            case BASKET:
                element = "basket";
                break;
        }
        String sql = "select " + element + " from Users where userId = ?";
        String oldData = "";
        String updateString = "";
        try{

            oldData = jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);

            if (addOrDelete == AddOrDelete.ADD) {
                updateString = appendData(oldData, newData);

            } else {
                updateString = deleteData(oldData, newData);
                updateString = updateString.substring(1, updateString.length()-1);
            }
            String updateSql = "update Users set " + element + " = ? where userId = ?";
            jdbcTemplate.update(updateSql, updateString, userId);
        } catch(EmptyResultDataAccessException e) {

            updateString = appendData(oldData, newData);
            String updateSql = "update Users set " + element + " = ? where userId = ?";
            jdbcTemplate.update(updateSql, updateString, userId);
        }
    }

    private String deleteData(String oldData, Integer newData) {
        if (oldData == null) return null;
        String[] array = oldData.split(",");
        List<Integer> toInteger = new ArrayList<>();

        for (String st : array) {
            try {
                int num = Integer.parseInt(st.trim());
                toInteger.add(num);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        toInteger.remove(newData);

        return String.valueOf(toInteger);
    }

    private String appendData(String oldData, Integer newData) {
        if (oldData == null) return Integer.toString(newData);
        return oldData + "," + Integer.toString(newData);
    }
    @Override
    public List<Integer> getCommentHistory(Long userId) {
        try {
            String currentHistory = jdbcTemplate.queryForObject("select commentHistory from Users where userId = ?", new Object[]{userId},
                    String.class);
            currentHistory = currentHistory.substring(1, currentHistory.length()-1);
            List<Integer> commentHistory = pareTEXTData(currentHistory);
            return commentHistory;
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
    @Override
    public List<Integer> getPostHistory(Long userId) {
        try {
            String currentHistory = jdbcTemplate.queryForObject("select postHistory from Users where userId = ?", new Object[]{userId},
                    String.class);
            currentHistory = currentHistory.substring(1, currentHistory.length() - 1);
            List<Integer> postHistory = pareTEXTData(currentHistory);
            return postHistory;
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Integer> getBasket(Long userId) {
        try {
            String currentHistory = jdbcTemplate.queryForObject("select basket from Users where userId = ?", new Object[]{userId},
                    String.class);
            List<Integer> basket = pareTEXTData(currentHistory);
            return basket;
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private List<Integer> pareTEXTData(String cHistory) {
        List<Integer> result = new ArrayList<>();

        if (cHistory.isEmpty()) {
            return result;
        }
        String[] array = cHistory.split(",");
        for (int i = 0; i < array.length; ++i) {
            try {
                result.add(Integer.parseInt(array[i].trim()));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Long getLastUserId() {
        try {
            Long id =  jdbcTemplate.queryForObject("select userId from Users order by userId desc", Long.class);
            return id;
        } catch(EmptyResultDataAccessException e){
            return 1L;
        }
    }

}
