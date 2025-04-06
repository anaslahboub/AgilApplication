package com.ensa.miniproject.repository;



import com.ensa.miniproject.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Integer> {
    List<Sprint> findByDays(Long days);
    List<Sprint> findBySprintBacklogIsNull();
}
