package com.lrrauseo.serverauth.controller;

import com.lrrauseo.serverauth.dto.CreatedUserDto;
import com.lrrauseo.serverauth.dto.NewUserDto;
import com.lrrauseo.serverauth.dto.UserAuth;
import com.lrrauseo.serverauth.security.util.JwtUtil;
import com.lrrauseo.serverauth.service.UserService;
import java.nio.file.attribute.UserPrincipalNotFoundException;
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
import org.springframework.web.bind.annotation.RequestMethod;
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

  @GetMapping("/{id}")
  public ResponseEntity getUserByEntity(String id) {
    try {
      return ResponseEntity.ok(userService.findById(id));
    } catch (UserPrincipalNotFoundException | UsernameNotFoundException ex) {
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
  public ResponseEntity<String> validate(@RequestBody UserAuth userAuth) {
    try {
      var userAuthenticated = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          userAuth.getLogin(),
          userAuth.getPassword()
        )
      );

      return new ResponseEntity<>(
        jwtUtil.generateToken(userAuthenticated),
        HttpStatus.ACCEPTED
      );
    } catch (Exception e) {
      return new ResponseEntity<>(
        "Login/Senha inv??lidos",
        HttpStatus.UNAUTHORIZED
      );
    }
  }
}
