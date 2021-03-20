package com.library.library.daoLayer;

import com.library.library.entity.Book;
import com.library.library.exception.BookException;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.BookDataRepository;
import com.library.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class BooksDaoImpl implements BookDao {
    private final Logger logger = LoggerFactory.getLogger(BooksDaoImpl.class);

    private final BookDataRepository repository;

    private final AuditBook auditBook;

    private final BookService bookService;

    @Autowired
    public BooksDaoImpl(BookDataRepository repository, AuditBook auditBook, BookService bookService){
        this.repository = repository;
        this.auditBook = auditBook;
        this.bookService = bookService;
    }


    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }


    public ResponseEntity createBook(Book book){
        try{
            saveBook(bookService.tryCreateBook(book));
            logger.info("Book " + book + " successfully created");
            return new ResponseEntity("book successfully created", HttpStatus.OK);
        }catch (BookException e){
            logger.info(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public Book getBookById(long id){
        try {
            return bookService.getBookById(id);
        }catch (BookException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    public ResponseEntity deleteBookById(long id){

       try {

           return bookService.deleteBook(id);

       }catch (NoSuchElementException e){

           logger.error("Book with id " + id + " doesn't create");
           return new ResponseEntity("Book with id " + id + " doesn't create", HttpStatus.NOT_FOUND);

       }catch (BookException e){

           return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);

       }
    }

    public ResponseEntity editBook(Book book){

        try {

             saveBook(bookService.editBookByParams(book));
             return new ResponseEntity("Book changed " + book, HttpStatus.OK);

        }catch (BookException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);

        }
    }

    public Book findByBookNameAndAuthor(String bookName, String authorName, String authorSurName){

        if(auditBook.isBookCreated(bookName, authorName, authorSurName)) {
            return repository.findBookByAuthorNameAndAuthorSurNameAndName(authorName, authorSurName, bookName);

        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book with this fields doesn't create");
        }
    }

    public void saveBook(Book book){
        repository.save(book);
    }


}
