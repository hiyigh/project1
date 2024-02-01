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
    void setUserHistory(Long userId, Integer newData, HistoryType type);
    List<Integer> getPostHistory(Long userId);
    List<Integer> getCommentHistory(Long userId);
    List<Integer> getBasket(Long userId);
    User find(Long userId);
    void add(User user);
    Long getLastUserId();
}
