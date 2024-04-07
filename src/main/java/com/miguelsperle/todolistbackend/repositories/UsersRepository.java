package com.miguelsperle.todolistbackend.repositories;

import com.miguelsperle.todolistbackend.entities.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, String> {
    Optional<UsersEntity> findByEmail(String email);
}
