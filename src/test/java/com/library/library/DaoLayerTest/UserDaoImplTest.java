package com.library.library.DaoLayerTest;

import com.library.library.daoLayer.BooksDaoImpl;
import com.library.library.daoLayer.UserDaoImpl;
import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.repository.UserDataRepository;
import com.library.library.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    @Mock
    private UserDataRepository dataRepository;

    @Mock
    private BooksDaoImpl booksDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDaoImpl userDao;

    @Test
    public void createUserTest(){

        User user = new User();
        user.setId(1);
        user.setUserName("Ivan");
        user.setSurName("Ivanenko");

        Mockito.doReturn(user)
                .when(userService)
                .tryCreateUser(user);

        Assert.assertEquals(new ResponseEntity("User created", HttpStatus.OK), userDao.createUser(user));
    }

    @Test
    public void findUserTest(){

        User user = new User();
        user.setId(1);
        user.setUserName("Ivan");
        user.setSurName("Ivanenko");

        Mockito.doReturn(user)
                .when(userService)
                .getUser(1);

        Assert.assertEquals(user, userDao.findUser(1));

    }

    @Test
    public void findUserByFields(){

        User user = new User();
        user.setId(1);
        user.setUserName("Ivan");
        user.setSurName("Ivanenko");

        Mockito.doReturn(user)
                .when(userService)
                .getUser("Ivan", "Ivanenko");

        Assert.assertEquals(user, userDao.findUser("Ivan", "Ivanenko"));
    }

    @Test
    public void findUserByBookTest(){

        Book harryPotter = new Book();
        harryPotter.setId(10);
        harryPotter.setName("Harry Potter");
        harryPotter.setAuthorName("Joan");
        harryPotter.setAuthorSurName("Roaling");

        User user = new User();
        user.setId(1);
        user.setSurName("Ivan");
        user.setUserName("Ivanenko");

        Set<Book> books = new HashSet<>();
        books.add(harryPotter);

        user.setBooks(books);

        Mockito.doReturn(user)
                .when(userService)
                .getUserByBook(harryPotter);

        Assert.assertEquals(user, userDao.findUser(harryPotter));
    }

    @Test
    public void deleteUserByIdTest(){

        User user = new User();
        user.setId(1);
        user.setSurName("Ivan");
        user.setUserName("Ivanenko");

        Mockito.doReturn(user)
                .when(userService)
                .deletedUser(1);

        Assert.assertEquals(new ResponseEntity("User deleted", HttpStatus.OK), userDao.deleteUserById(1));

    }

    @Test
    public void editUserTest(){

        User user = new User();
        user.setId(1);
        user.setSurName("Ivan");
        user.setUserName("Ivanenko");

        Mockito.doReturn(user)
                .when(userService)
                .editUser(user);

        Assert.assertEquals(new ResponseEntity("User edited", HttpStatus.OK), userDao.editUser(user));

    }

    @Test
    public void takeBookTest(){

        Book harryPotter = new Book();
        harryPotter.setId(10);
        harryPotter.setName("Harry Potter");
        harryPotter.setAuthorName("Joan");
        harryPotter.setAuthorSurName("Roaling");

        User user = new User();
        user.setId(1);
        user.setSurName("Ivan");
        user.setUserName("Ivanenko");
        user.setBooks(new HashSet<>());

        Mockito.doReturn(harryPotter)
                .when(booksDao)
                .getBookById(10);

        Mockito.doReturn(user)
                .when(userService)
                .getUser(1);


        Assert.assertNotSame(new ResponseEntity("Book " + harryPotter + " is taken", HttpStatus.OK), userDao.takeBook(10,1));
    }

    @Test
    public void takeBookByFieldsTest(){

        Book harryPotter = new Book();
        harryPotter.setId(10);
        harryPotter.setName("Harry Potter");
        harryPotter.setAuthorName("Joan");
        harryPotter.setAuthorSurName("Roaling");

        User user = new User();
        user.setId(1);
        user.setSurName("Ivan");
        user.setUserName("Ivanenko");
        user.setBooks(new HashSet<>());

        Mockito.doReturn(harryPotter)
                .when(booksDao)
                .findByBookNameAndAuthor("Harry Potter", "Joan", "Roaling");

        Mockito.doReturn(user)
                .when(userService)
                .getUser("Ivan", "Ivanenko");

        Assert.assertNotSame(new ResponseEntity("User " + user + " take the book " + harryPotter, HttpStatus.OK),
                userDao.takeBook("Ivan", "Ivanenko", "Harry Potter", "Joan", "Roaling"));

    }
}
