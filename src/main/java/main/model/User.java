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
    public User(Oauth2UserBuilder oauth2UserBuilder) {
        this.userName = oauth2UserBuilder.getUserName();
        this.userPassword = oauth2UserBuilder.getUserPassword();
        this.userEmail = oauth2UserBuilder.getUserEmail();
        this.role = oauth2UserBuilder.getRole();
        this.provider = oauth2UserBuilder.getProvider();
        this.providerId = oauth2UserBuilder.getProviderId();
    }
    @Getter
    public static class Oauth2UserBuilder {
        private String userName;
        private String userPassword;
        private String userEmail;
        private Role role;
        private String provider;
        private String providerId;
        public Oauth2UserBuilder() {
        }
        public Oauth2UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }
        public Oauth2UserBuilder userPassword(String userPassword){
            this.userPassword = userPassword;
            return this;
        }
        public Oauth2UserBuilder userEmail(String userEmail){
            this.userEmail = userEmail;
            return this;
        }
        public Oauth2UserBuilder role(Role role){
            this.role = role;
            return this;
        }
        public Oauth2UserBuilder provider(String provider){
            this.provider = provider;
            return this;
        }public Oauth2UserBuilder providerId(String providerId){
            this.providerId = providerId;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
