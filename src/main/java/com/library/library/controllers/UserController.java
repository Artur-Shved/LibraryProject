package com.library.library.controllers;

import com.library.library.entity.User;
import com.library.library.interfaceDao.UserDao;
import com.library.library.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao dao;

    @Autowired
    private UserDataRepository repository;


    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        return dao.createUser(user);
    }

    @GetMapping
    public List<User> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable long id){
        return dao.findUser(id);
    }

    @GetMapping("/findUserByNameAndSurName")
    public User findUser(@RequestParam String name, @RequestParam String userName){
        return dao.findUser(name, userName);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id){
        return dao.deleteUserById(id);
    }



    @PutMapping
    public ResponseEntity editUser(@RequestBody User user){
        return dao.editUser(user);
    }

    @GetMapping("/findAllByName/{name}")
    public List<User> findAllByUserName(@PathVariable String name) {
        return repository.findAllByUserName(name);
    }

}
