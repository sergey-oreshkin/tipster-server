package com.home.tipster.security.service;

import com.home.tipster.exception.UnknownRegistrationProviderException;
import com.home.tipster.users.model.RegistrationProvider;
import com.home.tipster.users.model.UserEntity;
import com.home.tipster.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        UserEntity user = null;
        if ("github".equals(userRequest.getClientRegistration().getRegistrationId())) {
            Integer id = oAuth2User.getAttribute("id");
            String name = oAuth2User.getAttribute("name") != null ? oAuth2User.getAttribute("name") : oAuth2User.getAttribute("login");
            user = UserEntity.builder()
                    .id(String.valueOf(id))
                    .name(name)
                    .pictureUrl(oAuth2User.getAttribute("avatar_url"))
                    .registrationProvider(RegistrationProvider.GITHUB)
                    .build();
        } else if ("google".equals(userRequest.getClientRegistration().getRegistrationId())) {
            user = UserEntity.builder()
                    .id(oAuth2User.getAttribute("sub"))
                    .name(oAuth2User.getAttribute("name"))
                    .pictureUrl(oAuth2User.getAttribute("picture"))
                    .registrationProvider(RegistrationProvider.GOOGLE)
                    .build();
        } else {
            throw new UnknownRegistrationProviderException(
                    String.format("Unknown registration provider: %s", userRequest.getClientRegistration().getRegistrationId())
            );
        }
        usersRepository.save(user);
        log.info("Save or update user with id:{}, name:{}, pictureUrl:{}, registrationProvider:{}",
                user.getId(), user.getName(), user.getPictureUrl(), user.getRegistrationProvider().name());
        return oAuth2User;
    }
}
