package com.tinyhappytrip.oauth2.service;

import com.tinyhappytrip.oauth2.user.OAuth2UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class OAuth2UserPrincipal implements OAuth2User, UserDetails {
    private final OAuth2UserInfo userInfo;
    private Collection<? extends GrantedAuthority> authorities;

    public OAuth2UserPrincipal(OAuth2UserInfo oAuth2UserInfo, Collection<? extends GrantedAuthority> authorities) {
        this.userInfo = oAuth2UserInfo;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userInfo.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userInfo.getEmail();
    }
}