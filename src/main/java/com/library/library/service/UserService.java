package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.exception.BookException;
import com.library.library.exception.UserException;
import com.library.library.interfaceDao.AuditUser;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDataRepository userDataRepository;

    private final AuditUser auditUser;

    private final BookDao bookDao;

    @Autowired
    public UserService(UserDataRepository userDataRepository, AuditUser auditUser, BookDao bookDao){
        this.userDataRepository = userDataRepository;
        this.auditUser = auditUser;
        this.bookDao = bookDao;

    }

    public User tryCreateUser(User user){
        if(auditUser.isUserAlredyCreated(user.getUserName(), user.getSurName())){
            throw new UserException("User with the same fields already created");
        }else if(!auditUser.isUserFieldsNotNull(user)){
            throw new UserException("User " + user + " have some empty fields");
        }

        return user;
    }

    public User getUser(long id){
        try{
            return userDataRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException e){
            logger.error("getUserById() from UserService class throw UserException because User with id " + id + " doesn't create");
            throw new UserException("User with id " + id + " doesn't create");
        }
    }

    public User getUser(String userName, String userSurName){
        if(auditUser.isUserAlredyCreated(userName, userSurName)){
            return userDataRepository.findByUserNameAndSurName(userName, userSurName);
        }else{
            throw new UserException("User with name " + userName + " and sur name " + userSurName + " doesnt create");
        }
    }

    public User deletedUser(long id){
        try{
            User user = getUser(id);

            if(user.getBooks().size() > 0){
                removeBooksFromUser(user);
                logger.info("all Book in user are deleted");
            }
            return user;

        }catch (UserException e){

            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void removeBooksFromUser(User user){

        Set<Book> books = user.getBooks();

        for(Book book : books){
            book.setBookFree(true);
            bookDao.saveBook(book);
            logger.info("Book" + book + " was deleted");
        }

        user.getBooks().clear();
        userDataRepository.save(user);
    }

    public void removeBooksFromUser(Book book){

        User user = getUserByBook(book);
        user.getBooks().remove(book);
        book.setBookFree(true);
        userDataRepository.save(user);
        bookDao.saveBook(book);

    }

    public User getUserByBook(Book book){

        if(!book.isBookFree()){
            return userDataRepository.findByBooks(book);
        }else{
            throw new BookException("book is free to take");
        }
    }

    public User editUser(User user){
        try{
            tryCreateUser(user);
            User oldUser = getUser(user.getId());
            oldUser.setUserName(user.getUserName());
            oldUser.setSurName(user.getSurName());
            return oldUser;
        }catch (UserException e){
            logger.error(e.getMessage());
            throw new UserException(e.getMessage());
        }
    }

}
