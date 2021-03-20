package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.exception.BookException;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.repository.BookDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookService {
    private Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookDataRepository bookRepository;

    @Autowired
    private AuditBook auditBook;


    public Book tryCreateBook(Book book) {
        if (auditBook.isBookCreated(book.getName(), book.getAuthorName(), book.getAuthorSurName())) {
            throw new BookException("Book " + book + " with this fields has already created");
        } else if (!auditBook.isBookElementsNotNull(book)) {
            throw new BookException("Book " + book + " have some null fields");
        }
        return book;
    }

    public Book getBookById(long id) {
        try {
            return bookRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            logger.error("getBookById() from BookService class throw BookException because book with id " + id + " doesn't create");
            throw new BookException("Book with this id " + id + " doesn't create");
        }
    }


    public ResponseEntity deleteBook(long id) {

        Book book = getBookById(id);

        if (auditBook.isBookFreeToTake(book)) {
            bookRepository.delete(book);
            logger.info("Book " + book + " is successfully deleted");
            return new ResponseEntity("Book " + book + " deleted", HttpStatus.OK);

        } else {
            logger.error("delete() from BookService class throw BookException because the Book is not free to take");
            throw new BookException("this book is in the some user");
        }
    }

    public Book editBookByParams(Book book) {
        tryCreateBook(book);
          Book oldBook = getBookById(book.getId());
          oldBook.setName(book.getName());
          oldBook.setAuthorName(book.getAuthorName());
          oldBook.setAuthorSurName(book.getAuthorSurName());
          return oldBook;
    }
}
