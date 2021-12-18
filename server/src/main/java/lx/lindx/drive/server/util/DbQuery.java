package lx.lindx.drive.server.util;

public enum DbQuery {
  DROP_USERS("dropUsers"),
  CREATE_USERS("createUsers"),
  INSERT_USER("insertUser"),
  UPDATE_USER("updateUser"),
  DELETE_USER("deleteUser"),
  SELECT_USER_BY_ID("selectUserById"),
  SELECT_USER_BY_EMAIL("selectUserByEmail"),
  SELECT_USER_BY_KEY("selectUserByKey"),
  SELECT_USER_BY_NAME("selectUserByName"),
  SELECT_ALL_USERS("selectAllUsers");

  private final String query;

  DbQuery(final String query) {
    this.query = query;
  }

  public String query() {
    return this.query;
  }
}
