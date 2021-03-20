package com.library.library.service;


import com.library.library.entity.Book;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.repository.BookDataRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private BookDataRepository bookDataRepository;

    @Mock
    private AuditBook auditBook;

    @InjectMocks
    private BookService bookService;

    @Test
    public void getBookByIdSuccessfullyTest(){
        Book book = new Book();
        book.setName("Harry Potter");
        book.setAuthorName("Joan");
        book.setAuthorSurName("Roaling");
        book.setId(10);
        Optional<Book> user = Optional.of(book);

        Mockito.doReturn(user)
                .when(bookDataRepository)
                .findById(10L);

        Assert.assertEquals(book, bookService.getBookById(10L));
    }

    @Test
    public void tryCreateBookTest(){
        Book book = new Book();
        book.setId(10);
        book.setName("White fang");
        book.setAuthorName("Djek");
        book.setAuthorSurName("London");

        Mockito.doReturn(false)
                .when(auditBook)
                .isBookCreated(book.getName(), book.getAuthorName(),book.getAuthorSurName());

        Mockito.doReturn(true)
                .when(auditBook)
                .isBookElementsNotNull(book);

        Assert.assertEquals(book, bookService.tryCreateBook(book));
    }

    @Test
    public void deleteBookTest(){
        Book book = new Book();
        book.setId(10);
        book.setName("Harry Potter");
        book.setAuthorName("Joan");
        book.setAuthorSurName("Roaling");
        Optional<Book> optionalBook = Optional.of(book);

        Mockito.doReturn(optionalBook)
                .when(bookDataRepository)
                .findById(10L);

        Mockito.doReturn(true)
                .when(auditBook)
                .isBookFreeToTake(book);

        Assert.assertEquals(new ResponseEntity("Book " + book + " deleted", HttpStatus.OK), bookService.deleteBook(10));
    }

    @Test
    public void editBookByParamsTest(){
        Book book = new Book();
        book.setId(5);
        book.setName("THE FOUR WINDS");
        book.setAuthorName("Kristin");
        book.setAuthorSurName("Hannah");
        Optional<Book> optionalBook = Optional.of(book);

        Mockito.doReturn(false)
                .when(auditBook)
                .isBookCreated(book.getName(), book.getAuthorName(),book.getAuthorSurName());

        Mockito.doReturn(true)
                .when(auditBook)
                .isBookElementsNotNull(book);

        Mockito.doReturn(optionalBook)
                .when(bookDataRepository)
                .findById(5L);

        Assert.assertEquals(book, bookService.editBookByParams(book));
    }



}
