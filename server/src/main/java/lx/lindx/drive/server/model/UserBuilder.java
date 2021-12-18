package lx.lindx.drive.server.model;

import lombok.Getter;

@Getter
public class UserBuilder {
  private long id;
  private String username;
  private String email;
  private String password;
  private String authCode;
  private String key;

  public UserBuilder setId(final long id) {
    this.id = id;
    return this;
  }

  public UserBuilder setUsername(final String username) {
    this.username = username;
    return this;
  }

  public UserBuilder setEmail(final String email) {
    this.email = email;
    return this;
  }

  public UserBuilder setPassword(final String password) {
    this.password = password;
    return this;
  }

  public UserBuilder setAuthCode(final String authCode) {
    this.authCode = authCode;
    return this;
  }

  public UserBuilder setKey(final String key) {
    this.key = key;
    return this;
  }

  public User build() {
    return new User(this);
  }
}
