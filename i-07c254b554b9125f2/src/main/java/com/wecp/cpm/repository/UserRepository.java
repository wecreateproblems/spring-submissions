package com.wecp.cpm.repository;

import java.util.List;

import com.wecp.cpm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data repository for the User entity. Use correct annotation and extend the class with necessary Spring Data class
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> getAllUsers();
    public User createUser(User user);
    public User getUser(Long id);

}
