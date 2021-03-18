package com.library.library.audit;

import com.library.library.entity.Book;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.repository.BookDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuditBookImpl implements AuditBook {
    @Autowired
    private BookDataRepository repository;

    @Override
    public boolean isBookCreatedById(long id){
        try{
            Book book = repository.findById(id).orElseThrow();
            book = null;
            return true;
        }catch (NoSuchElementException e){
            return false;
        }

    }

    public boolean isBookFreeToTake(Book book){
        if(book.isBookFree()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isBookCreated(String bookName, String authorName, String authorSurName){
        try{
            Book book = repository.
                    findBookByAuthorNameAndAuthorSurNameAndName(authorName,authorSurName, bookName);
            if(!isBookNotNull(book)){
                return false;
            }else {
                book = null;
                return true;
            }
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean isBookElementsNotNull(Book book){
        return !book.getName().isEmpty() && !book.getAuthorName().isEmpty() && !book.getAuthorSurName().isEmpty();
    }

    @Override
    public boolean isBookNotNull(Book book) {
        return book != null;
    }
}
