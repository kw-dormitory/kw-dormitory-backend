package com.kw.kwdn.domain.security.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.security.dto.MemberDetails;
import com.kw.kwdn.domain.security.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("custom userDetailsService start");
        Optional<Member> optionalMember = memberRepository.findOneById(userId);
        if (optionalMember.isEmpty()) return null;
        Member member = optionalMember.get();

        UserInfo userInfo = UserInfo.builder()
                .userId(userId)
                .token(member.getToken())
                .name(member.getName())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .photoUrl(member.getPhotoUrl())
                .build();
        log.info("custom userDetailsService end");
        return new MemberDetails(userInfo, member.getId(), member.getId(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
