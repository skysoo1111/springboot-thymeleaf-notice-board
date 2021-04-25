package com.webservice.boarddemo.controller;

import com.webservice.boarddemo.advice.exception.CEmailSigninFailedException;
import com.webservice.boarddemo.entity.User;
import com.webservice.boarddemo.repository.UserRepository;
import com.webservice.boarddemo.response.DefaultResponse;
import com.webservice.boarddemo.response.ResponseHandler;
import com.webservice.boarddemo.service.ResponseService;
import com.webservice.boarddemo.util.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Sign"})
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final ResponseService responseService;
  private final PasswordEncoder passwordEncoder;
  private final ResponseHandler responseHandler;

  @ApiOperation(value = "로그인", notes = "이메일로 회원 로그인을 수행한다.")
  @PostMapping("/signin")
  public ResponseEntity<DefaultResponse<Object>> signin(@ApiParam(value = "회원ID: 이메일", required = true) @RequestParam String uid,
      @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
      HttpServletRequest request) {
    User user = userRepository.findByUid(uid).orElseThrow(CEmailSigninFailedException::new);

    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new CEmailSigninFailedException();

    log.info("##### user.getId() : {}",user.getId());
    String token = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles());

    return ResponseEntity.ok().body(responseHandler.createResponse(token,request));
  }

  @ApiOperation(value = "회원가입", notes = "회원가입을 수행한다.")
  @PostMapping("/signup")
  public ResponseEntity<DefaultResponse<Object>> signup(@ApiParam(value = "회원ID: 이메일", required = true) @RequestParam String uid,
      @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
      @ApiParam(value = "이름", required = true) @RequestParam String name,
      HttpServletRequest request) {

    User singupUser = User.builder()
        .uid(uid)
        .password(passwordEncoder.encode(password))
        .name(name)
        .roles(Collections.singletonList("ROLE_USER"))
        .build();

    // TODO: 2021/04/25 이미 가입 되어있는 회원은 save 아닌 info로 알려주는 로직 필요
    userRepository.save(singupUser);

    return ResponseEntity.ok().body(responseHandler.createResponse(singupUser, request));
  }
}
