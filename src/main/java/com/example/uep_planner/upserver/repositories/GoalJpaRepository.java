package com.example.uep_planner.upserver.repositories;

import com.example.uep_planner.upserver.entities.Goal;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalJpaRepository extends JpaRepository<@NonNull Goal, @NonNull Long> {

    @Query(value = """
    SELECT * FROM goals
    WHERE user_id = :user_id
    AND (:status IS NULL OR status = :status)
    ORDER BY
    CASE
        WHEN status = "achieved" THEN 1
        ELSE 0
    END,
    CASE
        WHEN status = "achieved" THEN target_date
        ELSE NULL
    END,
    CASE
        WHEN status = "active" THEN target_date
        ELSE NULL
    END """, nativeQuery = true)
    List<Goal> findAllForUser(@Param("user_id") Long userId, @Param("status") String status);

    @Query(value = """
            SELECT * FROM goals
            WHERE user_id = :user_id
            AND id = :goal_id
            """, nativeQuery = true)
    Optional<Goal> findByIdForUser(@Param("user_id") Long userId, @Param("goal_id") Long goalId);

}
