package com.kw.kwdn.domain.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MemberDetails extends User {
    private String id;
    public MemberDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
        this.id = userId;
    }
}
