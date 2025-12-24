package com.example.uep_planner.upserver.repositories;

import com.example.uep_planner.upserver.entities.Motivation;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotivationJpaRepository extends JpaRepository<@NonNull Motivation, @NonNull Long> {

    @Query(value = """
    SELECT * FROM motivations
    WHERE user_id = :user_id
    """, nativeQuery = true)
    List<Motivation> findAllForUser(@Param("user_id") Long userId, @Param("status") String status);

    @Query(value = """
            SELECT * FROM motivations
            WHERE user_id = :user_id
            AND id = :motivation_id
            """, nativeQuery = true)
    Optional<Motivation> findByIdForUser(@Param("user_id") Long userId, @Param("motivation_id") Long motivationId);

}
