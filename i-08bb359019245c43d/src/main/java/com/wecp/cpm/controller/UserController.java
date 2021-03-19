package com.wecp.cpm.controller;

import com.wecp.cpm.domain.User;
import com.wecp.cpm.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for managing Users. You can use {@link UserRepository} class to implement Spring JPA repository and inject here.
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserRepository userRespository;
    UserController(UserRepository userRespository){
        this.userRespository = userRespository;
    }


    /**
     * {@code GET  /user} : get all the Users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     * of users in body.
     */
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok().body(userRespository.findAll());
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
        return userRespository.findById(id)
        .map(record -> ResponseEntity.ok().body(record))
        .orElse( ResponseEntity.notFound().build());
    }

    /**
     * {@code POST  /user} : Create a new user.
     *
     * @param user the user to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the new user
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUsername("userName");
        user.setId(12345678910L);
        userRespository.save(user);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /user/:id} : delete the "id" user.
     *
     * @param id the id of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRespository.findById(id)
        .map(record -> {
            userRespository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}