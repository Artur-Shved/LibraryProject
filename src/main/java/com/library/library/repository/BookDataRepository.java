package com.library.library.repository;

import com.library.library.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDataRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
    Book findBookByAuthorNameAndAuthorSurNameAndName(String authorName, String authorSurName, String bookName);
}
