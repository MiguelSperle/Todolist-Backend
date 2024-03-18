package com.miguelsperle.todolist.repositories;

import com.miguelsperle.todolist.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UsersEntity, String> {
    UserDetails findByEmail(String email);
}


