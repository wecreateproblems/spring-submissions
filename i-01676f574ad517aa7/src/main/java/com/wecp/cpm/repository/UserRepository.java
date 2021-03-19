package com.wecp.cpm.repository;

import java.util.List;

import com.wecp.cpm.domain.User;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for the User entity. Use correct annotation and extend the class with necessary Spring Data class
 */
public interface UserRepository extends JpaRepository<User,Integer>{
    @Query("from User where id =?")
    User getAllUsersById(Long id);

    @Query("delete from User where id =?")
    List<User> deleteAllUsersById(Long id);

}
