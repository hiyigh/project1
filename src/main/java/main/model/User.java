package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.model.enumeration.Role;

import java.util.ArrayList;
import java.util.List;

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

    private String basket;
    private String commentHistory;
    private String postHistory;

    private String provider;
    private String providerId;
    @Builder
    public User(String userName, String userEmail, String userPassword, boolean userSex, Role role) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userSex = userSex;
        this.role = role;
    }
    @Builder
    public User(String userName, String userPassword, String userEmail, Role role, String provider, String providerId) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
