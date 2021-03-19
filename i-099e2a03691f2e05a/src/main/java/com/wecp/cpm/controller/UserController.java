package com.wecp.cpm.controller;

import com.wecp.cpm.domain.User;
import com.wecp.cpm.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Users. You can use {@link UserRepository} class to implement Spring JPA repository and inject here.
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

@Autowired
UserRepository userRepository;

    /**
     * {@code GET  /user} : get all the Users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of users in body.
     */
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(userList);
    }

    /**
     * {@code GET  /user/:id} : get the "id" User.
     *
     * @param id the id of the user to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     * the user, or if does not exist, return with status "noContent".
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
         return ResponseEntity.ok().body(user.get());  
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * {@code POST  /user} : Create a new user.
     *
     * @param user the user to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the new user
     */
    @PostMapping("/user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }else{
                userRepository.save(user);
               Optional<User> checkUser=userRepository.findById(user.getId());
               if(checkUser.isPresent()){
                   return ResponseEntity.ok(null);
               }
               else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  
               }
           }
    }

    /**
     * {@code DELETE  /user/:id} : delete the "id" user.
     *
     * @param id the id of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

}