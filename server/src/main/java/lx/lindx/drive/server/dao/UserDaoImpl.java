package lx.lindx.drive.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lx.lindx.drive.server.model.User;
import lx.lindx.drive.server.model.UserBuilder;
import lx.lindx.drive.server.service.DataBaseService;
import lx.lindx.drive.server.util.Util;

import static lx.lindx.drive.server.util.DbQuery.CREATE_USERS;
import static lx.lindx.drive.server.util.DbQuery.DROP_USERS;
import static lx.lindx.drive.server.util.DbQuery.INSERT_USER;
import static lx.lindx.drive.server.util.DbQuery.SELECT_USER_BY_ID;
import static lx.lindx.drive.server.util.DbQuery.SELECT_USER_BY_EMAIL;
import static lx.lindx.drive.server.util.DbQuery.SELECT_USER_BY_KEY;
import static lx.lindx.drive.server.util.DbQuery.SELECT_USER_BY_NAME;
import static lx.lindx.drive.server.util.DbQuery.UPDATE_USER;
import static lx.lindx.drive.server.util.DbQuery.DELETE_USER;
import static lx.lindx.drive.server.util.DbQuery.SELECT_ALL_USERS;;

public class UserDaoImpl implements IUserDao {

  private DataBaseService db = new DataBaseService();
  private PreparedStatement statement;
  private ResultSet resultSet;

  @Override
  public void add(User user) {

    db.connect();
    statement = db.prepareStatement(Util.getQuery(INSERT_USER.query()));

    try {
      statement.setString(1, user.getUserName());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getAuthCode());
      statement.setString(5, user.getKey());
      statement.executeUpdate();
      statement.close();

    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();
  }

  @Override
  public void delete(User user) {

    db.connect();
    statement = db.prepareStatement(Util.getQuery(DELETE_USER.query()));

    try {
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();
  }

  @Override
  public User getUserById(Long id) {

    User user = null;

    db.connect();
    statement = db.prepareStatement(Util.getQuery(SELECT_USER_BY_ID.query()));

    try {
      statement.setLong(1, id);
      resultSet = statement.executeQuery();

      user = parseResult(resultSet);

      statement.execute();
      statement.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();
    return user;
  }

  @Override
  public void update(User user) {

    db.connect();
    statement = db.prepareStatement(Util.getQuery(UPDATE_USER.query()));

    try {
      statement.setLong(6, user.getId());
      
      statement.setString(1, user.getUserName());
      statement.setString(2, user.getEmail());
      statement.setString(3, user.getPassword());
      statement.setString(4, user.getAuthCode());
      statement.setString(5, user.getKey());
      
      statement.executeUpdate();
      statement.close();

    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();
  }

  @Override
  public User getUserByEmail(String email) {

    User user = null;

    db.connect();
    statement = db.prepareStatement(Util.getQuery(SELECT_USER_BY_EMAIL.query()));

    try {
      statement.setString(1, email);
      resultSet = statement.executeQuery();

      user = parseResult(resultSet);

      statement.execute();
      statement.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();

    return user;
  }

  @Override
  public User getUserByName(String name) {

    User user = null;

    db.connect();
    statement = db.prepareStatement(Util.getQuery(SELECT_USER_BY_NAME.query()));

    try {

      statement.setString(1, name);
      resultSet = statement.executeQuery();

      user = parseResult(resultSet);

      statement.execute();
      statement.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();
    return user;
  }

  @Override
  public User getUserByKey(final String key) {

    User user = null;

    db.connect();
    statement = db.prepareStatement(Util.getQuery(SELECT_USER_BY_KEY.query()));

    try {
      statement.setString(1, key);
      resultSet = statement.executeQuery();

      user = parseResult(resultSet);

      statement.execute();
      statement.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();
    return user;
  }

  @Override
  public List<User> listUsers() {

    List<User> users = new ArrayList<>();

    db.connect();
    statement = db.prepareStatement(Util.getQuery(SELECT_ALL_USERS.query()));

    try {
      resultSet = statement.executeQuery();

      while (resultSet.next()) {

        users.add(
            new UserBuilder()
                .setId(resultSet.getInt("id"))
                .setUsername(resultSet.getString("userName"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setAuthCode(resultSet.getString("authCode"))
                .setKey(resultSet.getString("user_key"))
                .build());
      }

      statement.execute();
      statement.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }

    db.disconnect();

    return users;
  }

  private User parseResult(ResultSet resultSet) {

    User user = null;

    try {
      if (resultSet.next()) {
        user = new UserBuilder()
            .setId(resultSet.getInt("id"))
            .setUsername(resultSet.getString("userName"))
            .setEmail(resultSet.getString("email"))
            .setPassword(resultSet.getString("password"))
            .setAuthCode(resultSet.getString("authCode"))
            .setKey(resultSet.getString("user_key"))
            .build();
      }
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }
    return user;
  }
}
