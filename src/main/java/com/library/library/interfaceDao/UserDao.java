package com.library.library.interfaceDao;

import com.library.library.entity.Book;
import com.library.library.entity.User;

public interface UserDao {
    String createUser(User user);
    User findUserById(long id);
    String deleteUserById(long id);
    void removeBookByIdInUser(User user, Book book);
    User editUser(User user);
}
