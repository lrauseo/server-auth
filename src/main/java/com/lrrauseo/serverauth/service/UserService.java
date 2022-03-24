package com.lrrauseo.serverauth.service;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.model.UserEntity;
import java.util.List;

public interface UserService {
  CreatedUserDto criar(NewUserDto newUser);

  List<CreatedUserDto> findAll();

  CreatedUserDto findByLogin(String login);
}
