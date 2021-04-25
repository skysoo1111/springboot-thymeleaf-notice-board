package com.webservice.boarddemo.mockmvctest;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.webservice.boarddemo.entity.User;
import com.webservice.boarddemo.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * MockMVC 테스트 및 Security 테스트
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUp() {
    userRepository.save(User.builder()
        .uid("skysoo1111@mock.mvc")
        .name("mockmvctest")
        .password(passwordEncoder.encode("1234"))
        .roles(Collections.singletonList("ROLE_USER"))
    .build());
  }

  @Test
  public void signin() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("uid", "skysoo1111@mock.mvc");
    params.add("password","1234");
    mockMvc.perform(post("/v1/signin").params(params))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.responseMessage").value("OK"))
        .andExpect(jsonPath("$.statusCode").value(200));
  }

  @Test
  public void signup() throws Exception {
    long epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("uid", "skysoo_"+epochTime+"@mock.mvc");
    params.add("password","1234");
    params.add("name","skysoo_"+epochTime);
    mockMvc.perform(post("/v1/signup").params(params))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.responseMessage").value("OK"))
        .andExpect(jsonPath("$.statusCode").value(200));
  }

  @Test
  @WithMockUser(username = "mockUser", roles = {"ADMIN"})
  public void accessDenied() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
    .get("/v1/users"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/exception/accessdenied"));
  }
}
