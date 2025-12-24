package com.example.uep_planner.upserver.repositories;

import com.example.uep_planner.upserver.entities.Task;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskJpaRepository extends JpaRepository<@NonNull Task, @NonNull Long> {

    @Query(value = """
    SELECT * FROM tasks
    WHERE user_id = :user_id
    AND (:status IS NULL OR status = :status)
    """, nativeQuery = true)
    List<Task> findAllForUser(@Param("user_id") Long userId, @Param("status") String status);

    @Query(value = """
            SELECT * FROM tasks
            WHERE user_id = :user_id
            AND id = :task_id
            """, nativeQuery = true)
    Optional<Task> findByIdForUser(@Param("user_id") Long userId, @Param("task_id") Long taskId);

}
