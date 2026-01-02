package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
}

