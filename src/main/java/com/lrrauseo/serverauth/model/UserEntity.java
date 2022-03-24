package com.lrrauseo.serverauth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Field.Write;

@Document(value = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserEntity {

  public UserEntity(UserEntity user) {
    cpf = user.getCpf();
    email = user.getEmail();
    enabled = user.isEnabled();
    id = user.getId();
    login = user.getLogin();
    password = user.getPassword();
    role = user.getRole();
  }

  @Id
  private String id;

  @Field(write = Write.NON_NULL)
  private String name;

  @Field(write = Write.NON_NULL)
  private String email;

  @Field(write = Write.NON_NULL)
  private String cpf;

  @Field(write = Write.NON_NULL)
  private String login;

  @Field(write = Write.NON_NULL)
  @ToString.Exclude
  private String password;

  @Field(write = Write.NON_NULL)
  private String role = "USER";

  @Field(write = Write.NON_NULL)
  private boolean enabled;
}
