package lx.lindx.drive.server.model;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private final long id;
  private final String userName;
  private final String email;
  private final String password;
  private final String authCode;
  private final String key;

  User(final UserBuilder userBuilder) {
    this.id = userBuilder.getId();
    this.userName = userBuilder.getUsername();
    this.email = userBuilder.getEmail();
    this.password = userBuilder.getPassword();
    this.authCode = userBuilder.getAuthCode();
    this.key = userBuilder.getKey();
  }

  @Override
  public int hashCode() {

    int hash = 17;
    hash *= 17 + id;
    hash *= 17 + userName.hashCode();
    hash *= 17 + authCode.hashCode();
    hash *= 17 + email.hashCode();
    hash *= 17 + password.hashCode();
    hash *= 17 + key.hashCode();

    return hash;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (o == null || this.getClass() != o.getClass())
      return false;

    User user = (User) o;

    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.userName, user.userName) &&
        this.authCode == user.authCode &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.key, user.key);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", userName='" + getUserName() + "'" +
        ", email='" + getEmail() + "'" +
        ", password='" + getPassword() + "'" +
        ", authCode='" + getAuthCode() + "'" +
        ", key='" + getKey() + "'" +
        "}";
  }
}
