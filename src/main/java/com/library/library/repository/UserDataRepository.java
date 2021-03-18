package com.library.library.repository;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends CrudRepository<User,Long> {
    List<User> findAll();
    List<User> findAllByUserName(String name);
    User findByUserName(String name);
    User findByBooks(Book book);
    User findByUserNameAndSurName(String userName, String userSurName);


}
