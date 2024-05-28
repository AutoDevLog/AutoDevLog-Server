package com.server.autodevlog.auth.service;

import com.server.autodevlog.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다.")
        );
    }
}
