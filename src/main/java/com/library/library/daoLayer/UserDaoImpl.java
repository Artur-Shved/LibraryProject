package com.library.library.daoLayer;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.exception.BookException;
import com.library.library.exception.UserException;
import com.library.library.interfaceDao.UserDao;
import com.library.library.repository.UserDataRepository;
import com.library.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Repository
public class UserDaoImpl implements UserDao {
    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private UserDataRepository repository;

    @Autowired
    private BooksDaoImpl booksDao;

    @Autowired
    private UserService userService;


    public ResponseEntity createUser(User user) {
        try{
            repository.save(userService.tryCreateUser(user));
            return new ResponseEntity("User created", HttpStatus.OK);
        }catch (UserException e){
            logger.warn(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public User findUser(long id) {
        try{
            return userService.getUser(id);
        }catch (UserException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public User findUser(String name, String surName){
        try{
            return userService.getUser(name, surName);
        }catch (UserException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public User findUser(Book book){
        return userService.getUserByBook(book);
    }

    public ResponseEntity deleteUserById(long id){

        repository.delete(userService.deletedUser(id));
        logger.info("User successfully deleted");
        return new ResponseEntity("User deleted", HttpStatus.OK);

    }

    public ResponseEntity editUser(User user){
        try{
            repository.save(userService.editUser(user));
            return new ResponseEntity("User edited", HttpStatus.OK);
        }catch (UserException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public ResponseEntity takeBook(long bookId, long userId){

        try {

            Book book = booksDao.getBookById(bookId);
            if(book.isBookFree()) {
                User user = findUser(userId);
                user.getBooks().add(book);
                book.setBookFree(false);
                repository.save(user);
                booksDao.saveBook(book);
                logger.info("Book " + book + " is taken");
                return new ResponseEntity("Book " + book + " is taken", HttpStatus.OK);
            }else{
                logger.info("Book " + book + " is in some user");
                return new ResponseEntity("Book " + book + " is not free to take", HttpStatus.CONFLICT);
            }
        }catch (NoSuchElementException e){
            logger.error("book by id " + bookId + " is not create");
            return new ResponseEntity("book id " + bookId + " or user id " + userId + " is invalid", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity takeBook(String userName, String userLastName , String bookName, String authorName, String authorSurName){
        try{
            Book book = booksDao.findByBookNameAndAuthor(bookName, authorName, authorSurName);
            User user = findUser(userName, userLastName);
            user.getBooks().add(book);
            book.setBookFree(false);
            booksDao.saveBook(book);
            repository.save(user);
            logger.info("User " + user + " take the book " + book);
            return new ResponseEntity("User " + user + " take the book " + book, HttpStatus.OK);
        }catch (Exception e){
            logger.warn("User with name " + userName + " and last name " + userLastName + " didn't take the book " +
                    " with name " + bookName + " and author name " + authorName +  " and author last name " + authorSurName);
            return new ResponseEntity("User with name " + userName + " didn't take the book", HttpStatus.ACCEPTED);
        }
    }

    public ResponseEntity removeBook(Book book){
        try{
            userService.removeBooksFromUser(book);
            return new ResponseEntity("Book " + book + " removed", HttpStatus.OK);
        }catch (BookException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
