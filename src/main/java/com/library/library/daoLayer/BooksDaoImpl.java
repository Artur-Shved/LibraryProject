package com.library.library.daoLayer;

import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.BookDataRepository;
import com.library.library.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BooksDaoImpl implements BookDao {

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

    public String createBook(Book book){
        if(!auditBook.isBookCreated(book.getName(), book.getAuthorName(), book.getAuthorSurName())){
            if(auditBook.isBookElementsNotNull(book)){
                repository.save(book);
                return "Book is save";
            }else{
                return "some names is empty";
            }
        }else{
            return book.getName() + " alredy created";
        }
    }

    public Book getBookById(long id){
        if(auditBook.isBookCreatedById(id)){
            return repository.findById(id).orElseThrow();
        }else{
            return null;
        }
    }

    public Object getUserByBookId(long id){
        if(auditBook.isBookCreatedById(id)) {
            Book book = repository.findById(id).get();
            if(!auditBook.isBookFreeToTake(book)) {
                return userDataRepository.findByBooks(book);
            }else{
                return book.toString() + " must be free to take";
            }
        }else{
            return "book alredy have not been created";
        }
    }

    public String deleteBookById(long id){
        if(auditBook.isBookCreatedById(id)){
            Book book = repository.findById(id).orElseThrow();
            if(auditBook.isBookFreeToTake(book)) {
                repository.delete(book);
                return "Book was deleted";
            }else{
                return "Book is in the user";
            }
        }else{
            return "can't find a book";
        }

    }

    public Book editBook(Book book){
        Book oldBook = getBookById(book.getId());
        if(oldBook != null && !auditBook.isBookCreated(book.getName(), book.getAuthorName(), book.getAuthorSurName())) {
            repository.save(book);
        }
        return oldBook;
    }

    public Book findByBookNameAndAuthor(String bookName, String authorName, String authorSurName){
        if(auditBook.isBookCreated(bookName, authorName, authorSurName)) {
            return repository.findBookByAuthorNameAndAuthorSurNameAndName(authorName, authorSurName, bookName);
        }else{
            return null;
        }
    }


}
