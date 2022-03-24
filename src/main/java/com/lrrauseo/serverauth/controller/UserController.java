package com.lrrauseo.serverauth.controller;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.dto.UserAuth;
import com.lrrauseo.serverauth.security.util.JwtUtil;
import com.lrrauseo.serverauth.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;

  @PostMapping
  public ResponseEntity<CreatedUserDto> criarUsuario(
    @RequestBody NewUserDto user
  ) {
    return ResponseEntity.ok(userService.criar(user));
  }

  @GetMapping
  public ResponseEntity<List<CreatedUserDto>> getAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @PostMapping("/authenticate")
  public ResponseEntity<String> validate(@RequestBody UserAuth userAuth) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          userAuth.getLogin(),
          userAuth.getPassword()
        )
      );
      return new ResponseEntity<>(
        jwtUtil.generateToken(userAuth.getLogin()),
        HttpStatus.ACCEPTED
      );
    } catch (Exception e) {
      return new ResponseEntity<>(
        "Login/Senha inválidos",
        HttpStatus.UNAUTHORIZED
      );
    }
  }
}
