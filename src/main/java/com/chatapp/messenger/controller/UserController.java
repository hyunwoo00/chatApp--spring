package com.chatapp.messenger.controller;


import com.chatapp.messenger.service.oauth.OAuth2Service;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final OAuth2Service oAuth2Service;
    private final OAuth2AuthorizedClientService authAuthorizedClientService;

    private final String SIGNOUT_URL = "https://kauth.kakao.com/oauth/logout";
    private final String SIGNOUT_REDIRECT_URL = "http://localhost:8080/user/kakao/logout";
    @RequestMapping("/user/signout")
    public String signout() {


        Map<String, OAuth2ClientProperties.Registration> registrations = oAuth2ClientProperties.getRegistration();
        String clientId = registrations.get("kakao").getClientId();

        String url = UriComponentsBuilder.fromUriString(SIGNOUT_URL)
                .queryParam("client_id", clientId)
                .queryParam("logout_redirect_uri", SIGNOUT_REDIRECT_URL)
                .toUriString();

        return "redirect:" + url;
    }

    @RequestMapping("/user/kakao/logout")
    public String kakao_signout(HttpServletResponse response){
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @RequestMapping("/user/kakao/withdraw")
    public void kakao_withdraw(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication == null) return;

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();

        OAuth2AuthorizedClient client =
                authAuthorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();

        oAuth2Service.unlinkKakao(accessToken);
    }


}
