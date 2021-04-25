package com.webservice.boarddemo.advice.exception;

import com.webservice.boarddemo.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {

  @GetMapping("/entrypoint")
  public CommonResult entryPointException() {
    throw new CAuthenticationEntryPointException();
  }

}
