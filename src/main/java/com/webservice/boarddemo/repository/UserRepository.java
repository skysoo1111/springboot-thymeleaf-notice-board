package com.webservice.boarddemo.repository;

import com.webservice.boarddemo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUid(String email);
}
