package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.model.enumeration.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class User extends Time {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private boolean userSex;
    private Role role;
    private List<Integer> basket;
    private List<Integer> commentHistory;
    private List<Integer> postHistory;
    @Builder
    public void builder(Long userId, String userName, String userEmail, String userPassword,
                        boolean userSex, LocalDateTime createdTime, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userSex = userSex;
        super.createdTime = createdTime;
        this.role = role;
        this.basket = new ArrayList<>();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(userEmail, user.userEmail) && Objects.equals(userPassword, user.userPassword) && Objects.equals(userSex, user.userSex);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userEmail, userPassword, userSex);
    }
}
