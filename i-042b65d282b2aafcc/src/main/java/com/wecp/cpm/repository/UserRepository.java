package com.wecp.cpm.repository;

import java.util.List;

import com.wecp.cpm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for the User entity. Use correct annotation and extend the class with necessary Spring Data class
 */
@SuppressWarnings("unused")
public interface UserRepository {
    List<User> getAllUsersDetail();
    
    User getUser(Long id);

    User createUser(User user);

    void deleteUser(Long id);
}
