package main.service;

import lombok.RequiredArgsConstructor;
import main.model.User;
import main.repository.method.UserRepoMethod;
import main.service.method.UserMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserMethod {
    private final UserRepoMethod userRepoMethod;
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
}
