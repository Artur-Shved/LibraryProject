package com.library.library.audit;

import com.library.library.entity.Book;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.repository.BookDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuditBookImpl implements AuditBook {
    @Autowired
    private BookDataRepository repository;

    @Override
    public boolean isBookCreatedById(long id){

            return repository.findById(id).isPresent();
    }

    public boolean isBookFreeToTake(Book book){
        return book.isBookFree();
    }

    public boolean isBookCreated(String bookName, String authorName, String authorSurName){
        return repository.findBookByAuthorNameAndAuthorSurNameAndName(authorName,authorSurName,bookName) != null;
    }

    public boolean isBookElementsNotNull(Book book){
        return !book.getName().isEmpty() && !book.getAuthorName().isEmpty() && !book.getAuthorSurName().isEmpty();
    }

    @Override
    public boolean isBookNotNull(Book book) {
        return book != null;
    }
}
