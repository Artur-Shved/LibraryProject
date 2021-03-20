package com.library.library.controllers;

import com.library.library.entity.Book;
import com.library.library.interfaceDao.BookDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {



    @Autowired
    private BookDao dao;



    @PostMapping
    public ResponseEntity createBook(@RequestBody Book book){
        return dao.createBook(book);
    }

    @GetMapping
    public List<Book> findAll(){
        return dao.findAll();
    }

    @GetMapping("/findByNameAndAuthor")
    public Book findByNameAndAuthor(@RequestParam String name,
                                    @RequestParam String authorName,
                                    @RequestParam String authorSurName){

        return dao.findByBookNameAndAuthor(name, authorName, authorSurName);
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable long id){

        return dao.getBookById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable long id){
        return dao.deleteBookById(id);
    }

    @PutMapping
    public ResponseEntity editBook(@RequestBody Book book){

             return dao.editBook(book);

    }

}
