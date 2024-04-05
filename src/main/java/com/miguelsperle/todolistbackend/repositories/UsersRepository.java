package com.miguelsperle.todolistbackend.repositories;

import com.miguelsperle.todolistbackend.entities.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, String> {
    Optional<UserDetails> findByEmail(String email);
}
