package main.service.method;

import main.model.User;
import main.model.enumeration.HistoryType;

import java.util.HashSet;
import java.util.List;

public interface UserMethod {
    User getUserById(Long userId);
    User getUserByNameOrNull(String userName);
    User getUserByEmailOrNull(String userEmail);
    List<User> getAllUsers();
    List<Integer> getCommentHistory(Long userId);
    List<Integer> getPostHistory(Long userId);
    List<Integer> getBasket(Long userId);
    void updateUserHistory(Integer userId, Integer newData, HistoryType type);
    User find(Long userId);
    void add(User user);

    Long getLastUserId();
}
