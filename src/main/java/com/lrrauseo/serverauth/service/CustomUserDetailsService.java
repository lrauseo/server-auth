package com.lrrauseo.serverauth.service;

import com.lrrauseo.serverauth.model.CustomUserDetails;
import com.lrrauseo.serverauth.model.UserEntity;
import com.lrrauseo.serverauth.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    Optional<UserEntity> user = repository.findByLogin(username);
    return new CustomUserDetails(
      user.orElseThrow(
        () -> new UsernameNotFoundException("Usuário não encontrado")
      )
    );
  }
}
