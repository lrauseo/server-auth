package com.lrrauseo.serverauth.repository;

import com.lrrauseo.serverauth.model.UserEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
  Optional<UserEntity> findByLogin(String login);
}
