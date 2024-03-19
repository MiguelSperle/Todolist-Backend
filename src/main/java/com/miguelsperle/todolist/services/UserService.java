package com.miguelsperle.todolist.services;

import com.miguelsperle.todolist.entities.UsersEntity;
import com.miguelsperle.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
       var user = this.userRepository.findByEmail(email);
       return (UsersEntity) user;
    }

    public UsersEntity saveUser(UsersEntity usersEntity){
        return this.userRepository.save(usersEntity);
    }

    public Optional<UsersEntity> findUserById(String id){
        var user = this.userRepository.findById(id);

        return user;
    }
}
