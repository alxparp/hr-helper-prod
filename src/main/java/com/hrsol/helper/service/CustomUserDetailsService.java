package com.hrsol.helper.service;

import com.hrsol.helper.model.CustomUserDetails;
import com.hrsol.helper.entity.Role;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow();

        return new CustomUserDetails.Builder()
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmail(user.getEmail())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .isDisabled(user.isDisabled())
                .isLocked(user.isLocked())
                .withAuthorities(getGrantedAuthorities(user.getRoles()))
                .build();
    }

    private Set<SimpleGrantedAuthority> getGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }
}
