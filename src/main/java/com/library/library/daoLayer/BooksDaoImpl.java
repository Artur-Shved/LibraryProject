package com.library.library.daoLayer;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.BookDataRepository;
import com.library.library.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class BooksDaoImpl implements BookDao {
    Logger logger = LoggerFactory.getLogger(BooksDaoImpl.class);

    @Autowired
    private BookDataRepository repository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private AuditBook auditBook;


    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    public ResponseEntity createBook(Book book){
        if(!auditBook.isBookCreated(book.getName(), book.getAuthorName(), book.getAuthorSurName())){
            if(auditBook.isBookElementsNotNull(book)){
                repository.save(book);
                logger.info("Book: " + book + " is save");
                return new ResponseEntity("Book is save", HttpStatus.OK);
            }else{
                logger.warn("check your empty fields");
                return new ResponseEntity("some fields is empty", HttpStatus.ACCEPTED);
            }
        }else{
            logger.warn("Book: " + book + " is already created");
            return new ResponseEntity(book.getName() + " alredy created", HttpStatus.ACCEPTED);
        }
    }

    public Book getBookById(long id){
        if(auditBook.isBookCreatedById(id)){
            logger.info("you finded a book");
            return repository.findById(id).orElseThrow();
        }else{
            logger.warn("Book with this id " + id + " doesn't create");
            throw new NoSuchElementException("Book with this id " + id + " doesnt create");
        }
    }

    public User getUserByBookId(long id){
        if(auditBook.isBookCreatedById(id)) {
            Book book = repository.findById(id).get();
            if(!auditBook.isBookFreeToTake(book)) {
                logger.info("user found");
                return userDataRepository.findByBooks(book);
            }else{
                logger.error("book is free to take " + book);
                 throw new NullPointerException();
            }
        }else{
            logger.error("book with this id doesn't create " + id);
             throw new NoSuchElementException("book with this id " + id + " doesnt create");
        }
    }

    public ResponseEntity deleteBookById(long id){
        if(auditBook.isBookCreatedById(id)){
            Book book = repository.findById(id).orElseThrow();
            if(auditBook.isBookFreeToTake(book)) {
                repository.delete(book);
                logger.info("user was deleted");
                return new ResponseEntity("book was deleted", HttpStatus.OK);
            }else{
                logger.warn("can't delete this book " + book + " it is the some user");
                return new ResponseEntity("book is in the some user", HttpStatus.ACCEPTED);
            }
        }else{
            logger.warn("Book by id " + id + " doesn't create");
            return new ResponseEntity("book by this id doesnt created", HttpStatus.ACCEPTED);
        }

    }

    public Book editBook(Book book){
        Book oldBook = getBookById(book.getId());
        if(oldBook != null && !auditBook.isBookCreated(book.getName(), book.getAuthorName(), book.getAuthorSurName())) {
            logger.info("you saved a book " + book);
            repository.save(book);
        }else{
            logger.warn("book didn't save " + book);
        }
        return oldBook;
    }

    public Book findByBookNameAndAuthor(String bookName, String authorName, String authorSurName){
        if(auditBook.isBookCreated(bookName, authorName, authorSurName)) {
            return repository.findBookByAuthorNameAndAuthorSurNameAndName(authorName, authorSurName, bookName);
        }else{
            throw new NoSuchElementException("book with this fields doesn't create");
        }
    }


}
