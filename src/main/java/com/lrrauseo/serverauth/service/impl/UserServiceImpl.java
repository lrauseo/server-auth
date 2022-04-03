package com.lrrauseo.serverauth.service.impl;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.mapper.UserMapper;
import com.lrrauseo.serverauth.model.UserEntity;
import com.lrrauseo.serverauth.repository.UserRepository;
import com.lrrauseo.serverauth.service.UserService;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
  public UserEntity findById(String id) throws UserPrincipalNotFoundException {
    return userRepository
      .findById(id)
      .orElseThrow(
        () -> new UserPrincipalNotFoundException("Usuário não encontrado")
      );
  }
  // @Override
  // public UserDetails loadUserByUsername(String username)
  //   throws UsernameNotFoundException {
  //   var user = this.findByLogin(username);
  //   if (user == null) throw new UsernameNotFoundException(
  //     "Usuario [" + username + "] não encontrato"
  //   );
  //   return new CustomUserDetails(UserMapper.INSTANCE.toUsuario(user));
  // }

}
