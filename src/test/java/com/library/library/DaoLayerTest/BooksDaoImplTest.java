package com.library.library.DaoLayerTest;

import com.library.library.audit.AuditBookImpl;
import com.library.library.daoLayer.BooksDaoImpl;
import com.library.library.entity.Book;
import com.library.library.repository.BookDataRepository;
import com.library.library.service.BookService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class BooksDaoImplTest {

    @Mock
    private BookDataRepository bookDataRepository;

    @Mock
    private AuditBookImpl auditBook;

    @Mock
    private BookService bookService;

    @InjectMocks
    BooksDaoImpl booksDao;

    @Test
    public void findAllTest(){
        Book harryPotter = new Book();
        harryPotter.setId(10);
        harryPotter.setName("Harry Potter");
        harryPotter.setAuthorName("Joan");
        harryPotter.setAuthorSurName("Roaling");

        Book whiteFang = new Book();
        whiteFang.setId(5);
        whiteFang.setName("White Fang");
        whiteFang.setAuthorName("Djek");
        whiteFang.setAuthorSurName("London");

        List<Book> list = asList(harryPotter, whiteFang);

        Mockito.doReturn(list)
                .when(bookDataRepository)
                .findAll();

        Assert.assertEquals(list, booksDao.findAll());
    }

    @Test
    public void createBookTest(){

        Book whiteFang = new Book();
        whiteFang.setId(5);
        whiteFang.setName("White Fang");
        whiteFang.setAuthorName("Djek");
        whiteFang.setAuthorSurName("London");

        Mockito.doReturn(whiteFang)
                .when(bookService)
                .tryCreateBook(whiteFang);

        Assert.assertEquals(new ResponseEntity("book successfully created", HttpStatus.OK), booksDao.createBook(whiteFang));

    }

    @Test
    public void getBookByIdTest(){

        Book whiteFang = new Book();
        whiteFang.setId(5);
        whiteFang.setName("White Fang");
        whiteFang.setAuthorName("Djek");
        whiteFang.setAuthorSurName("London");

        Mockito.doReturn(whiteFang)
                .when(bookService)
                .getBookById(5);

        Assert.assertEquals(whiteFang, booksDao.getBookById(5));
    }

    @Test
    public void deleteBookByIdTest(){

        Book whiteFang = new Book();
        whiteFang.setId(5);
        whiteFang.setName("White Fang");
        whiteFang.setAuthorName("Djek");
        whiteFang.setAuthorSurName("London");

        Mockito.doReturn(new ResponseEntity("Book " + whiteFang + " deleted", HttpStatus.OK))
                .when(bookService)
                .deleteBook(5);

        Assert.assertEquals(new ResponseEntity("Book " + whiteFang + " deleted", HttpStatus.OK), booksDao.deleteBookById(5));

    }

    @Test
    public void editBookTest(){
        Book whiteFang = new Book();
        whiteFang.setId(5);
        whiteFang.setName("White Fang");
        whiteFang.setAuthorName("Djek");
        whiteFang.setAuthorSurName("London");

        Mockito.doReturn(whiteFang)
                .when(bookService)
                .editBookByParams(whiteFang);

        Assert.assertEquals(new ResponseEntity("Book changed " + whiteFang, HttpStatus.OK), booksDao.editBook(whiteFang));
    }

    @Test
    public void findByBookNameAndAuthorTest(){

        Book harryPotter = new Book();
        harryPotter.setId(10);
        harryPotter.setName("Harry Potter");
        harryPotter.setAuthorName("Joan");
        harryPotter.setAuthorSurName("Roaling");

        Mockito.doReturn(true)
                .when(auditBook)
                .isBookCreated("Harry Potter", "Joan", "Roaling");

        Mockito.doReturn(harryPotter)
                .when(bookDataRepository)
                .findBookByAuthorNameAndAuthorSurNameAndName("Joan", "Roaling", "Harry Potter");

        Assert.assertEquals(harryPotter, booksDao.findByBookNameAndAuthor("Harry Potter", "Joan", "Roaling"));
    }

}
