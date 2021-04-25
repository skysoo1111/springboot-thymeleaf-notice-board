package com.webservice.boarddemo.response;

public enum StatusCode {
  OK(200,"OK"),
  CREATE(201,"CREATE"),
  NO_CONTENT(204,"NO_CONTENT"),
  BAD_REQUEST(400,"BAD_REQUEST"),
  UNAUTHORIZED(401,"UNAUTHORIZED"),
  FORBIDDEN(403,"FORBIDDEN"),
  NOT_FOUND(404,"NOT_FOUND"),
  INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR"),
  DB_ERROR(600,"DB_ERROR");

  StatusCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int code;
  public String message;
}