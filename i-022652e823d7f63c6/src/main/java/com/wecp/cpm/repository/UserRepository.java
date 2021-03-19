package com.wecp.cpm.repository;

import java.util.Optional;

import com.wecp.cpm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the User entity. Use correct annotation and extend the class with necessary Spring Data class
 */
@Repository
public interface UserRepository extends JpaRepository<User,User> {

	Optional<User> findById(Long id);

}
