package com.lrrauseo.serverauth.service.impl;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.mapper.UserMapper;
import com.lrrauseo.serverauth.model.CustomUserDetails;
import com.lrrauseo.serverauth.model.UserEntity;
import com.lrrauseo.serverauth.repository.UserRepository;
import com.lrrauseo.serverauth.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public CreatedUserDto criar(NewUserDto newUser) {
    UserEntity user = UserMapper.INSTANCE.toUsuario(newUser);
    var userCreated = this.userRepository.save(user);
    return UserMapper.INSTANCE.usuarioToUsuarioCriadoDto(userCreated);
  }

  @Override
  public List<CreatedUserDto> findAll() {
    var users = userRepository.findAll();
    var usersDto = UserMapper.INSTANCE.usuarioToUsuarioCriadoDto(users);
    return usersDto;
  }

  @Override
  public CreatedUserDto findByLogin(String login) {
    return UserMapper.INSTANCE.usuarioToUsuarioCriadoDto(
      userRepository.findByLogin(login).get()
    );
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    var user = this.findByLogin(username);
    if (user == null) throw new UsernameNotFoundException(
      "Usuario [" + username + "] não encontrato"
    );
    return new CustomUserDetails(UserMapper.INSTANCE.toUsuario(user));
  }
}
