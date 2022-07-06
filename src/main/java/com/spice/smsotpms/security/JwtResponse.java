package com.spice.smsotpms.security;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String username;
  private String mobileno;
  private List<String> roles;

  public JwtResponse(String accessToken, String username, String mobileno) {
    this.token = accessToken;
    this.username = username;
    this.mobileno = mobileno;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public String getMobileno() {
    return mobileno;
  }

  public void setMobileno(String mobileno) {
    this.mobileno = mobileno;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
