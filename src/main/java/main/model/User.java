package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class User extends Time {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private boolean userSex;
    private Role role;
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
