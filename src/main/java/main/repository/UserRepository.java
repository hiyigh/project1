package main.repository;

import lombok.RequiredArgsConstructor;
import main.model.enumeration.HistoryType;
import main.model.User;
import main.repository.method.UserRepoMethod;
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

    @Override
    public List<Integer> getCommentHistory(Long userId) {
        String cHistory = jdbcTemplate.queryForObject("select commentHistory from Users where userId = ?", new Object[]{userId},
                new BeanPropertyRowMapper<>(String.class));
        if (cHistory != null) {
            List<Integer> commentHistory = pareTEXTData(cHistory);
            return commentHistory;
        }
        return Collections.emptyList();
    }
    @Override
    public List<Integer> getPostHistory(Long userId) {
        String pHistory = jdbcTemplate.queryForObject("select * from Users where userId = ?", new Object[]{userId},
                new BeanPropertyRowMapper<>(String.class));
        if (pHistory != null) {
            List<Integer> postHistory = pareTEXTData(pHistory);
            return postHistory;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Integer> getBasket(Long userId) {
        String b = jdbcTemplate.queryForObject("select * from Users where userId = ?", new Object[]{userId},
                new BeanPropertyRowMapper<>(String.class));
        if (b != null) {
            List<Integer> basket = pareTEXTData(b);
            return basket;
        }
        return Collections.emptyList();
    }

    @Override
    public void updateUserHistory(Integer userId, Integer newData, HistoryType type) {
        String element = null;
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
        String oldData = jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
        String updateString = appendData(oldData, newData);
        String updateSql = "update Users set " + element + " = ? where userId = ?";
        jdbcTemplate.update(sql, updateString, userId);
    }
    private String appendData(String oldData, Integer newData) {
        if (oldData == null) return Integer.toString(newData);
        return oldData + "," + newData;
    }

    private List<Integer> pareTEXTData(String cHistory) {
        String[] array = cHistory.split(",");
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < array.length; ++i) {
            try {
                result.add(Integer.parseInt(array[i].trim()));
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
