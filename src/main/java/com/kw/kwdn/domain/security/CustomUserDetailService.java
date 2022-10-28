package com.kw.kwdn.domain.security;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if (userId == null) throw new IllegalStateException("userId가 없습니다.");
        Member member = memberRepository.findOneById(userId).orElseThrow(()-> new UsernameNotFoundException("해당하는 user id가 없습니다."));
        List<GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("USER"));
        MemberDetails memberDetails = new MemberDetails(member.getId(), "", auth);
        log.info("user details service is finished");
        return memberDetails;
    }
}
