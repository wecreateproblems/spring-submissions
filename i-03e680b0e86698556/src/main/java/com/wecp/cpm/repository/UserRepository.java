package com.wecp.cpm.repository;

import com.wecp.cpm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the User entity. Use correct annotation and extend
 * the class with necessary Spring Data class
 * 
 * @param <user>
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository<user> extends JpaRepository<user, Long> {

}
