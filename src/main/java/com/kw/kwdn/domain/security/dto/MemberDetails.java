package com.kw.kwdn.domain.security.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MemberDetails extends User {
    private UserInfo userInfo;

    public MemberDetails(UserInfo userInfo, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "MemberDetails{" +
                "userInfo=" + userInfo +
                '}';
    }
}
