package com.webservice.boarddemo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class BoardController {

  @Value("${val}")
  private String val;

  @GetMapping("main-boards")
  public ResponseEntity<String> getMainBoardList() {
    log.info(val);
    return ResponseEntity.ok().body(val);
  }
}
