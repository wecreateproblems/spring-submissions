package com.wecp.cpm.repository;

import java.util.ArrayList;
import java.util.List;

import com.wecp.cpm.domain.User;

import org.springframework.stereotype.Service;

@Service
public class UserService implements UserRepository {
    public List<User> gblListOfUsers = new ArrayList<>();
    @Override
    public List<User> getAllUsersDetail() {
        List<User> listOfUsers = gblListOfUsers;

        return listOfUsers;
    }

    @Override
    public User getUser(Long id) {
        User user = null;
       
        return user;
    }

    @Override
    public User createUser(User user) {
        gblListOfUsers.add(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
       
    }
    
}