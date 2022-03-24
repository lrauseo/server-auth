package com.lrrauseo.serverauth.mapper;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.model.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  //@Mapping(source = "numberOfSeats", target = "seatCount")
  CreatedUserDto usuarioToUsuarioCriadoDto(UserEntity user);

  NewUserDto usuarioToNovoUsuarioCriadoDto(UserEntity user);

  List<CreatedUserDto> usuarioToUsuarioCriadoDto(List<UserEntity> users);

  List<NewUserDto> usuarioToNovoUsuarioCriadoDto(List<UserEntity> users);

  UserEntity toUsuario(NewUserDto dto);

  UserEntity toUsuario(CreatedUserDto dto);
}
