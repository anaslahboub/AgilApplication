package com.ensa.miniproject.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AppUseDetailService implements UserDetailsService {
    private final UserAuthRep userAuthRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRep.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        List<GrantedAuthority> authorities  = userAuth.getRoles().stream().map(
                role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList()
        );

        return User.builder()
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .authorities(authorities)
                .build();

    }
}