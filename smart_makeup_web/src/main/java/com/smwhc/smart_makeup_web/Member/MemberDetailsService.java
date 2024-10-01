package com.smwhc.smart_makeup_web.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {
    @Autowired
    private final MemberRepository memberRepository;

    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findById(member_id);
 
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Member member = result.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (member.getMember_id().equals("admin")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN-USER"));
        }
        else {
            authorities.add(new SimpleGrantedAuthority("NORMAL-USER"));
        }

        return new User(member.getMember_id(), member.getMember_password(), authorities);
    }
}
