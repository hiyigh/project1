package main.service;

import lombok.RequiredArgsConstructor;
import main.dto.PrincipalDetails;
import main.model.User;
import main.model.enumeration.Role;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        User user = userRepository.getUserByEmailOrNull(email);

        if (user == null) {
            String provider = userRequest.getClientRegistration().getRegistrationId();
            String providerId = oAuth2User.getAttribute("sub");
            String userName = provider + "-" + providerId;

            String uuid = UUID.randomUUID().toString().substring(0,6);
            String password = "password" + uuid;
            Role role = Role.ROLE_USER; // 사용자의 역할 설정

            user = new User.Oauth2UserBuilder().userName(userName).userPassword(password).userEmail(email).role(role)
                    .provider(provider).providerId(providerId).build();
            userRepository.add(user);
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
