package com.library.library.controllers;

import com.library.library.entity.Book;
import com.library.library.interfaceDao.AuditBook;
import com.library.library.interfaceDao.BookDao;
import com.library.library.repository.BookDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookDataRepository repository;

    @Autowired
    private BookDao dao;

    @Autowired
    AuditBook auditDao;


    @PostMapping
    public ResponseEntity createBook(@RequestBody Book book){
        return dao.createBook(book);
    }

    @GetMapping
    public List<Book> findAll(){
        return repository.findAll();
    }

    @GetMapping("/findByNameAndAuthor")
    public Book findByNameAndAuthor(@RequestParam String name, String authorName,
                                                    String authorSurName){

        return dao.findByBookNameAndAuthor(authorName, authorSurName, name);
    }

    @GetMapping("/findById/{id}")
    public Book findById(@PathVariable long id){

            return dao.getBookById(id);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteBook(@PathVariable long id){
        return dao.deleteBookById(id);
    }

    @PostMapping("/editBook")
    public Book editUser(@RequestBody Book book){
        return dao.editBook(book);
    }

}
