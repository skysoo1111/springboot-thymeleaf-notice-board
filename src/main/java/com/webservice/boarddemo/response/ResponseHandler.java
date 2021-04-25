package com.webservice.boarddemo.response;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
  public DefaultResponse<Object> createResponse(Object object, HttpServletRequest request){

    return DefaultResponse.builder()
        .data(Optional.ofNullable(object).orElse(StatusCode.NOT_FOUND))
        .statusCode(StatusCode.OK.code)
        .responseMessage(StatusCode.OK.message)
        .build();
  }
}