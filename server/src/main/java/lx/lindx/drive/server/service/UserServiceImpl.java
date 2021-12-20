package lx.lindx.drive.server.service;

import java.util.List;

import lx.lindx.drive.server.dao.IUserDao;
import lx.lindx.drive.server.dao.UserDaoImpl;
import lx.lindx.drive.server.model.User;

public class UserServiceImpl implements IUserService {

  private IUserDao userDao;

  {
    userDao = new UserDaoImpl();
  }

  @Override
  public void add(User user) {
    userDao.add(user);
  }

  @Override
  public void delete(User user) {
    userDao.delete(user);
  }

  @Override
  public void update(final User user) {
    userDao.update(user);
  }

  @Override
  public User getUserById(final Long id) {
    return userDao.getUserById(id);
  }

  @Override
  public User getUserByEmail(final String email) {
    return userDao.getUserByEmail(email);
  }

  @Override
  public User getUserByName(final String name) {
    return userDao.getUserByName(name);
  }

  @Override
  public User getUserByKey(String key) {
    return userDao.getUserByKey(key);
  }

  @Override
  public List<User> listUsers() {
    return userDao.listUsers();
  }
}
