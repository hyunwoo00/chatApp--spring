package com.chatapp.messenger.service.oauth;

import com.chatapp.messenger.domain.User;
import com.chatapp.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    /**
     * 유저 정보를 받아와 등록
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        //유저 정보를 담고있음.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User: {}", oAuth2User);
        log.info("accessToken: {}", userRequest.getAccessToken().getTokenValue());

        final Map<String, Object> attributes = oAuth2User.getAttributes();
        //회원 고유 식별자
        final String oauthId = String.valueOf(attributes.get("id"));
        //oauth 종류(ex. 카카오, 구글)
        final String oauthType = userRequest.getClientRegistration().getRegistrationId();

        final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        final Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        String nickName = String.valueOf(profile.get("nickname"));
        String email =  String.valueOf(account.get("email"));

        //가입된 이메일이 없을 경우 회원가입.
        userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(new User(nickName, email, oauthId, oauthType)));

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id"
        );

    }

}
