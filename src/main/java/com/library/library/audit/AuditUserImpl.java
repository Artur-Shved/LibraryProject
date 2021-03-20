package com.library.library.audit;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.AuditUser;
import com.library.library.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuditUserImpl implements AuditUser {

    @Autowired
    UserDataRepository repository;

    public boolean isUserAlredyCreated(String userName, String userSurName){
         return repository.findByUserNameAndSurName(userName, userSurName) != null;

    }

    public boolean isUserAlredyCreated(long id){
        return repository.findById(id).isPresent();
    }

    public boolean isBookIsSame(Book a, Book b){
        return a.getName().equals(b.getName()) &&
                a.getAuthorName().equals(b.getAuthorName()) &&
                a.getAuthorSurName().equals(b.getAuthorSurName());
    }

    public boolean isUserNotNull(User user){
        return user != null;
    }

    public boolean isUserFieldsNotNull(User user){
        return !user.getSurName().isEmpty() && !user.getUserName().isEmpty();
    }

}
