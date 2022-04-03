package com.lrrauseo.serverauth.service;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.model.UserEntity;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface UserService {
  CreatedUserDto criar(NewUserDto newUser);

  List<CreatedUserDto> findAll();

  CreatedUserDto findByLogin(String login);

  UserEntity findById(String id) throws UserPrincipalNotFoundException;
}
