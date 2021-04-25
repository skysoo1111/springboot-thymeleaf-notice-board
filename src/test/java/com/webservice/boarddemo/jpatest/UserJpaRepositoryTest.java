package com.webservice.boarddemo.jpatest;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webservice.boarddemo.config.DatabaseConfiguration;
import com.webservice.boarddemo.entity.User;
import com.webservice.boarddemo.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * @DataJpaTest 사용시 dataSource 가 없다는 에러를 뱉을 것이다. 이를 해결하기 위해서 2가지 선택지가 있다.
 * 1. @DataJpaTest 대신 @SpringBootTest 를 사용
 * 2. 관련 빈을 사용하는 Configuration 을 @Import
 **/
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(DatabaseConfiguration.class)
public class UserJpaRepositoryTest {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void whenFindByUid_thenReturnUser() {
    String uid = "skysoo1111@test.com";
    String name = "skysoo1111";

    userRepository.save(User.builder()
        .uid(uid)
        .password(passwordEncoder.encode("1234"))
        .name(name)
        .roles(Collections.singletonList("ROLE_USER"))
        .build());

    Optional<User> user = userRepository.findByUid(uid);

    assertNotNull(user);
    assertTrue(user.isPresent());
    assertEquals(user.get().getName(), name);
    assertThat(user.get().getName(), is(name));
  }
}
