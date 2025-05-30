package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByUsername(String username);
    List<Developer> findBySpecialityIgnoreCase(String speciality);
    List<Developer> findByEquipe_Id(Long equipeId);
}
