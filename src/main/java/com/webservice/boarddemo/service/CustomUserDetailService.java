package com.webservice.boarddemo.service;

import com.webservice.boarddemo.advice.exception.CUserNotFoundException;
import com.webservice.boarddemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    return userRepository.findById(Long.valueOf(id)).orElseThrow(CUserNotFoundException::new);
  }
}
