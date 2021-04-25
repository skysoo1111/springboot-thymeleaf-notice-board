package com.webservice.boarddemo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultResponse<T> {
  private int statusCode;
  private String responseMessage;
  private T data;

  @Builder
  public DefaultResponse(int statusCode, String responseMessage, T data) {
    this.statusCode = statusCode;
    this.responseMessage = responseMessage;
    this.data = data;
  }
}