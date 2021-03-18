package com.library.library.daoLayer;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.interfaceDao.AuditUser;
import com.library.library.interfaceDao.UserDao;
import com.library.library.repository.BookDataRepository;
import com.library.library.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Access;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    BookDataRepository bookDataRepository;

    @Autowired
    UserDataRepository repository;

    @Autowired
    AuditUser auditUser;

    @Autowired
    AuditBook auditBook;

    public String createUser(User user) {
        if (auditUser.isUserAlredyCreated(user.getUserName(), user.getSurName())){
            return "User is alredy created";
        } else if(auditUser.isUserFieldsNotNull(user)){
            repository.save(user);
            return "User is save";
        }else{
            return "some fields in user are null";
        }
    }

    public User findUserById(long id) {
        if (auditUser.isUserAlredyCreated(id)) {
            return repository.findById(id).orElseThrow();
        } else {
            return null;
        }
    }

    public String deleteUserById(long id) {
        User user = findUserById(id);
        if(user != null){
            if(user.getBooks().size() > 0){
                deleteAllBooksInUser(user);
            }
            repository.delete(user);
            return "User was deleted";
        }else{
            return "can't find user";
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
                for (Book b : user.getBooks()) {
                    if (auditUser.isBookIsSame(b, book)) {
                        b.setBookFree(true);
                        bookDataRepository.save(b);
                        user.getBooks().remove(b);
                        repository.save(user);
                    }
                }
        }
    }

    public User editUser(User user){
        if(auditUser.isUserAlredyCreated(user.getId()) && auditUser.isUserNotNull(user) && auditUser.isUserFieldsNotNull(user)) {
            User oldUser = findUserById(user.getId());
            oldUser.setSurName(user.getSurName());
            oldUser.setUserName(user.getUserName());
            repository.save(oldUser);
            return oldUser;
        }else{
            return null;
        }
    }
}
