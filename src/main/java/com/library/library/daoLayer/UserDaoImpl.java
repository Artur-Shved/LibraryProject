package com.library.library.daoLayer;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.interfaceDao.AuditUser;
import com.library.library.interfaceDao.UserDao;
import com.library.library.repository.BookDataRepository;
import com.library.library.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class UserDaoImpl implements UserDao {
    Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    BookDataRepository bookDataRepository;

    @Autowired
    UserDataRepository repository;

    @Autowired
    AuditUser auditUser;

    @Autowired
    AuditBook auditBook;

    public ResponseEntity createUser(User user) {
        if (auditUser.isUserAlredyCreated(user.getUserName(), user.getSurName())){
            logger.warn("user is already create " + user);
            return new ResponseEntity("user alredy created ", HttpStatus.ACCEPTED);
        } else if(auditUser.isUserFieldsNotNull(user)){
            logger.info("You have saved " + user);
            repository.save(user);
            return new ResponseEntity("User is save " + user.toString(), HttpStatus.OK);
        }else{
            logger.warn("some fields in user are null " + user);
            return new ResponseEntity("some fields in user are null ", HttpStatus.ACCEPTED);
        }
    }

    public User findUserById(long id) {
        if (auditUser.isUserAlredyCreated(id)) {
            logger.info("User with id " + id + " found");
            return repository.findById(id).orElseThrow();
        } else {
            logger.warn("User with id " + id + " doesn't create");
            throw new NoSuchElementException("User with this id " + id + "doesnt create");
        }
    }

    public ResponseEntity deleteUserById(long id) {
        User user = findUserById(id);
        if(user != null){
            if(user.getBooks().size() > 0){
                deleteAllBooksInUser(user);
                logger.info("all book in user are deleted");
            }
            repository.delete(user);
            logger.info("user was deleted");
            return new ResponseEntity(user.toString() + " was deleted ", HttpStatus.OK);
        }else{
            logger.warn("can't find user by id " + id);
            return new ResponseEntity("can't find user by id: " + id, HttpStatus.ACCEPTED);
        }
    }

    public void deleteAllBooksInUser(User user){

        for(Book book : user.getBooks()){
            book.setBookFree(true);
            bookDataRepository.save(book);
            user.getBooks().remove(book);
        }
        repository.save(user);
    }

    public void removeBookByIdInUser(User user, Book book){
        if(auditUser.isUserNotNull(user) && auditBook.isBookNotNull(book)) {
            logger.info("User " + user + " is not null");
            logger.info("in User " + user + " books don't null");
                for (Book b : user.getBooks()) {
                    if (auditUser.isBookIsSame(b, book)) {
                        b.setBookFree(true);
                        bookDataRepository.save(b);
                        user.getBooks().remove(b);
                        repository.save(user);
                    }
                };
        }
    }

    public ResponseEntity editUser(User user){
        if(auditUser.isUserAlredyCreated(user.getId()) && auditUser.isUserNotNull(user) && auditUser.isUserFieldsNotNull(user)) {
            logger.info("User " + user + " is alredy created");
            logger.info("User " + user + " is not null");
            logger.info("Users " + user + " fields are not null");
            User oldUser = findUserById(user.getId());
            oldUser.setSurName(user.getSurName());
            oldUser.setUserName(user.getUserName());
            repository.save(oldUser);
            logger.info("User " + user + " have edited");
            return new ResponseEntity(user.toString() + " have edited " , HttpStatus.OK);
        }else{
            logger.warn("User " + user + " have not edited");
            return new ResponseEntity("user doesn't create " , HttpStatus.ACCEPTED);
        }
    }
}
