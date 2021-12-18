package lx.lindx.drive.server.dao;

import java.util.List;

import lx.lindx.drive.server.model.User;

public interface IUserDao {

  void add(final User user);

  void delete(final User user);

  void update(final User user);

  User getUserById(final Long id);

  User getUserByEmail(final String email);

  User getUserByName(final String name);

  User getUserByKey(final String key);

  List<User> listUsers();

}
