package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.critere.id = :critereId")
    Task findByCritereId(@Param("critereId") Long critereId);
}