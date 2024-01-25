package main.service;

import lombok.RequiredArgsConstructor;
import main.model.User;
import main.model.enumeration.HistoryType;
import main.repository.UserRepository;
import main.repository.method.UserRepoMethod;
import main.service.method.UserMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserMethod {
    private final UserRepository userRepoMethod;
    @Override
    public User getUserById(Long userId) {
        return userRepoMethod.getUserById(userId);
    }

    @Override
    public User getUserByNameOrNull(String userName) {
        return userRepoMethod.getUserByNameOrNull(userName);
    }

    @Override
    public User getUserByEmailOrNull(String userEmail) {
        return userRepoMethod.getUserByEmailOrNull(userEmail);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepoMethod.getAllUsers();
    }

    @Override
    public List<Integer> getCommentHistory(Long userId) {
        return userRepoMethod.getCommentHistory(userId);
    }

    @Override
    public List<Integer> getPostHistory(Long userId) {
        return userRepoMethod.getPostHistory(userId);
    }

    @Override
    public List<Integer> getBasket(Long userId) {
        return userRepoMethod.getBasket(userId);
    }

    @Override
    public void updateUserHistory(Integer userId, Integer newData, HistoryType type) {
        userRepoMethod.updateUserHistory(userId, newData, type);
    }
}
