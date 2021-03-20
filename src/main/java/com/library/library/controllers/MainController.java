package com.library.library.controllers;

import com.library.library.entity.User;
import com.library.library.interfaceDao.BookDao;
import com.library.library.interfaceDao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    BookDao bookDao;

    @Autowired
    UserDao userDao;

    @PostMapping("/userTakeABook")
    public ResponseEntity userTakeABook(@RequestParam String bookName,
                                        @RequestParam String authorName,
                                        @RequestParam String authorSurName,
                                        @RequestParam String userName,
                                        @RequestParam String userSurName){

        return userDao.takeBook(userName, userSurName, bookName, authorName, authorSurName);
    }

    @PostMapping("/userTakeABook/{user_id}/{book_id}")
    public ResponseEntity userTakeABook(@PathVariable long user_id, @PathVariable long book_id){
            return userDao.takeBook(book_id, user_id);
    }


    @PostMapping("/removeBookFromUser/{book_id}")
    public ResponseEntity removeBook(@PathVariable long book_id){
        return userDao.removeBook(bookDao.getBookById(book_id));
    }


    @GetMapping("/findUser/{bookId}")
    public User findUserByBook(@PathVariable long bookId){
        return userDao.findUser(bookDao.getBookById(bookId));
    }
}
