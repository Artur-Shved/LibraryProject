package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.AuditUser;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.UserDataRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserDataRepository dataRepository;

    @Mock
    private AuditUser auditUser;

    @InjectMocks
    UserService userService;

    @Test
    public void tryCreateUserTest(){
        User user = new User();
        user.setId(10);
        user.setUserName("Artur");
        user.setSurName("Shved");

        Mockito.doReturn(false)
                .when(auditUser)
                .isUserAlredyCreated(user.getUserName(), user.getSurName());

        Mockito.doReturn(true)
                .when(auditUser)
                .isUserFieldsNotNull(user);

        Assert.assertEquals(user, userService.tryCreateUser(user));
    }

    @Test
    public void getUserTest(){
        User user = new User();
        user.setId(10);
        user.setUserName("Artur");
        user.setSurName("Shved");

        Optional<User> optionalUser = Optional.of(user);
        Mockito.doReturn(optionalUser)
                .when(dataRepository)
                .findById(10L);

        Assert.assertEquals(user, userService.getUser(10));
    }

    @Test
    public void getUserByFieldsTest(){
        User user = new User();
        user.setId(10);
        user.setUserName("Artur");
        user.setSurName("Shved");

        Mockito.doReturn(true)
                .when(auditUser)
                .isUserAlredyCreated("Artur", "Shved");

        Mockito.doReturn(user)
                .when(dataRepository)
                .findByUserNameAndSurName("Artur", "Shved");

        Assert.assertEquals(user, userService.getUser("Artur", "Shved"));

    }

    @Test
    public void deleteUserTest(){
        User user = new User();
        user.setId(10);
        user.setUserName("Artur");
        user.setSurName("Shved");
        user.setBooks(new HashSet<>());

        Optional<User> optionalUser = Optional.of(user);

        Mockito.doReturn(optionalUser)
                .when(dataRepository)
                .findById(10L);


        Assert.assertEquals(user, userService.deletedUser(10));

    }

    @Test
    public void getUserByBookTest(){
        Book book = new Book();
        book.setId(10);
        book.setName("White Fang");
        book.setAuthorName("Djek");
        book.setAuthorSurName("London");
        book.setBookFree(false);

        User user = new User();
        user.setId(1);
        user.setUserName("Ivan");
        user.setSurName("Ivanenko");

        Mockito.doReturn(user)
                .when(dataRepository)
                .findByBooks(book);

        Assert.assertEquals(user, userService.getUserByBook(book));
    }

    @Test
    public void editUserTest(){
        User user = new User();
        user.setId(1);
        user.setUserName("Ivan");
        user.setSurName("Ivanenko");

        Optional<User> optionalUser = Optional.of(user);

        Mockito.doReturn(false)
                .when(auditUser)
                .isUserAlredyCreated(user.getUserName(), user.getSurName());

        Mockito.doReturn(true)
                .when(auditUser)
                .isUserFieldsNotNull(user);



        Mockito.doReturn(optionalUser)
                .when(dataRepository)
                .findById(1L);

        Assert.assertEquals(user, userService.editUser(user));
    }
}
