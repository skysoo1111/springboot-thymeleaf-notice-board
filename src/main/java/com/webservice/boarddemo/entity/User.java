package com.webservice.boarddemo.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, unique = true, length = 30)
  private String uid;
  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(nullable = false, length = 100)
  private String password;
  @Column(nullable = false, length = 100)
  private String name;

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private List<String> roles = new ArrayList<>();


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream().map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @JsonProperty(access = Access.WRITE_ONLY)
  @Override
  public String getUsername() {
    return this.uid;
  }

  // 계정이 만료가 안되었는지
  @JsonProperty(access = Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // 계정이 잠기지 않았는지
  @JsonProperty(access = Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  // 계정 패스워드 만료가 안되었는지
  @JsonProperty(access = Access.WRITE_ONLY)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // 계정이 사용 가능한지
  @Override
  public boolean isEnabled() {
    return false;
  }
}