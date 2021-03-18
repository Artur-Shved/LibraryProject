package com.library.library.controllers;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.BookDao;
import com.library.library.interfaceDao.UserDao;
import com.library.library.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDao dao;

    @Autowired
    BookDao bookDao;

    @Autowired
    UserDataRepository repository;


    @PostMapping
    public String createUser(@RequestBody User user){
        return dao.createUser(user);
    }

    @GetMapping
    public List<User> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable long id){
        return dao.findUserById(id);
    }


    @DeleteMapping("/{id}")
    public String deleteUserByName(@PathVariable long id){
        return dao.deleteUserById(id);
    }

    @PostMapping("/removeBooks/{book_id}/{user_id}")
    public String removeBookById(@PathVariable long book_id, @PathVariable long user_id){
        Book book = bookDao.getBookById(book_id);
        User user = dao.findUserById(user_id);
        try {
            if (!user.getBooks().isEmpty()) {
                dao.removeBookByIdInUser(user, book);
                return book.toString() + " was deleted";
            }
        }catch (NullPointerException e){
            return "Invalid user";
        }
        return user.toString() + " doesnt have this book";
    }

    @PostMapping("/editUser")
    public User editUser(@RequestBody User user){
        return dao.editUser(user);
    }

    @GetMapping("/findAllByName/{name}")
    public List<User> deleteAllByName(@PathVariable String name) {
        return repository.findAllByUserName(name);
    }

}
