package com.library.library.controllers;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.BookDataRepository;
import com.library.library.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @Autowired
    BookDataRepository bookRepository;

    @Autowired
    UserDataRepository userRepository;

    @Autowired
    BookDao dao;

    @PostMapping("/main")
    public String userGetABook(@RequestParam String bookName,
                               @RequestParam String authorName,
                               @RequestParam String authorSurName,
                               @RequestParam String userName){

        User user = userRepository.findByUserName(userName);
        Book book = bookRepository.
                findBookByAuthorNameAndAuthorSurNameAndName(authorName, authorSurName, bookName);

        book.setBookFree(false);
        bookRepository.save(book);
        user.getBooks().add(book);
        userRepository.save(user);
        return "Book was taken";
    }

    @GetMapping("/main")
    public String removeBook(@RequestParam long userId, @RequestParam long bookId){

        User user = userRepository.findById(userId).get();
        Book book = bookRepository.findById(bookId).get();
        for(Book b : user.getBooks()){
            if(b.getName().equals(book.getName())){
                user.getBooks().remove(b);
                book.setBookFree(true);
            }
        }

        bookRepository.save(book);
        userRepository.save(user);

        return "Book was return";
    }


    @GetMapping("/findUser/{bookId}")
    public Object findByBook(@PathVariable long bookId){
        return dao.getUserByBookId(bookId);
    }
}
