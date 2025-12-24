package com.example.uep_planner.upserver.repositories;

import com.example.uep_planner.upserver.entities.Schedule;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<@NonNull Schedule, @NonNull Long> {

    @Query(value = """
    SELECT * FROM schedules
    WHERE user_id = :user_id
    AND (:day IS NULL OR day = :day)
    ORDER BY start_time DESC
    """, nativeQuery = true)
    List<Schedule> findAllForUser(@Param("user_id") Long userId, @Param("day") String day);

    @Query(value = """
            SELECT * FROM schedules
            WHERE user_id = :user_id
            AND id = :schedule_id
            """, nativeQuery = true)
    Optional<Schedule> findByIdForUser(@Param("user_id") Long userId, @Param("schedule_id") Long scheduleId);

}
