package main.repository.method;

import main.model.User;
import main.model.enumeration.AddOrDelete;
import main.model.enumeration.HistoryType;

import java.util.List;

public interface UserRepoMethod {
    User getUserById(Long userId);

    User getUserByNameOrNull(String userName);

    User getUserByEmailOrNull(String userEmail);

    List<User> getAllUsers();
    List<Integer> getPostHistory(Long userId);

    void setUserHistory(Long userId, Integer newData, HistoryType type, AddOrDelete addOrDelete);

    List<Integer> getCommentHistory(Long userId);
    List<Integer> getBasket(Long userId);

    void add(User user);

    User find(Long userId);

    Long getLastUserId();


}
