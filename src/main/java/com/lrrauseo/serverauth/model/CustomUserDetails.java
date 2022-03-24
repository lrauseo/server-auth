package com.lrrauseo.serverauth.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
  private String email;
  private boolean enabled;
  private String login;
  private String password;
  private String role;

  public CustomUserDetails(UserEntity user) {
    email = user.getEmail();
    enabled = user.isEnabled();
    login = user.getLogin();
    password = user.getPassword();
    role = user.getRole();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var authorities = Arrays
      .stream(role.split(","))
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
