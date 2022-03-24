package com.lrrauseo.serverauth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CreatedUserDto {
  private String id;

  private String name;

  private String cpf;

  private String login;

  private String password;

  private String role = "USER";
}
