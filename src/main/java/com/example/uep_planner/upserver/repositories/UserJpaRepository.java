package com.example.uep_planner.upserver.repositories;

import com.example.uep_planner.upserver.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<@NonNull User, @NonNull Long> {
    Optional<User> findByEmail(String email);
}
