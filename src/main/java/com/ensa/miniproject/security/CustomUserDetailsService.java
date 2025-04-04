package com.ensa.miniproject.security;

import com.ensa.miniproject.entities.User;
import com.ensa.miniproject.repository.DeveloperRepository;
import com.ensa.miniproject.repository.ProductOwnerRepository;
import com.ensa.miniproject.repository.ScrumMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final DeveloperRepository developerRepository;
    private final ScrumMasterRepository scrumMasterRepository;
    private final ProductOwnerRepository productOwnerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<? extends User> user = developerRepository.findByUsername(username);
        if (user.isEmpty()) user = scrumMasterRepository.findByUsername(username);
        if (user.isEmpty()) user = productOwnerRepository.findByUsername(username);

        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}