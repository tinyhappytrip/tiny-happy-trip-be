package com.tinyhappytrip.oauth2.handler;

import com.tinyhappytrip.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.tinyhappytrip.oauth2.service.OAuth2UserPrincipal;
import com.tinyhappytrip.oauth2.user.OAuth2Provider;
import com.tinyhappytrip.oauth2.user.OAuth2UserInfo;
import com.tinyhappytrip.oauth2.user.OAuth2UserUnlinkManager;
import com.tinyhappytrip.oauth2.util.CookieUtils;
import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.security.jwt.JwtTokenProvider;
import com.tinyhappytrip.user.mapper.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.tinyhappytrip.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;
import static com.tinyhappytrip.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserMapper userMapper;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String targetUrl;

        targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }
        if ("login".equalsIgnoreCase(mode)) {
            OAuth2UserInfo oAuth2UserInfo = principal.getUserInfo();
            OAuth2Provider provider = oAuth2UserInfo.getProvider();
            String socialType = provider.getRegistrationId().toUpperCase();
            String email = oAuth2UserInfo.getEmail();
            String userImage = oAuth2UserInfo.getProfileImageUrl();
            if (!userMapper.selectByEmail(oAuth2UserInfo.getEmail()).isPresent()) {
                return UriComponentsBuilder.fromUriString("http://localhost:3000/signup?social=true")
                        .queryParam("email", email)
                        .queryParam("userImage", userImage)
                        .queryParam("socialType", socialType)
                        .build().toUriString();
            }

            JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

            log.info("email={}, name={}, nickname={}, accessToken={}, profileImageUrl={}",
                    principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getAccessToken(),
                    principal.getUserInfo().getProfileImageUrl()
            );
            String grantType = jwtToken.getGrantType();
            String accessToken = jwtToken.getAccessToken();
            String refreshToken = jwtToken.getRefreshToken();
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("grantType", grantType)
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();
        } else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();
            userMapper.deleteOAuthUser(principal.getUserInfo().getEmail());
            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}