package main.repository.method;

import main.model.User;

import java.util.List;

public interface UserRepoMethod {
    User getUserById(Long userId);

    User getUserByNameOrNull(String userName);

    User getUserByEmailOrNull(String userEmail);

    List<User> getAllUsers();
}
