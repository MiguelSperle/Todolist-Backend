package com.miguelsperle.todolist.services;

import com.miguelsperle.todolist.entities.UsersEntity;
import com.miguelsperle.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    public UsersEntity getUser() {
        var userDetails = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return (UsersEntity) userDetails;
    }

    public UsersEntity findUserByEmail(String email){
       var user = userRepository.findByEmail(email);
       return (UsersEntity) user;
    }

    public UsersEntity saveUser(UsersEntity usersEntity){
        return userRepository.save(usersEntity);
    }
}
