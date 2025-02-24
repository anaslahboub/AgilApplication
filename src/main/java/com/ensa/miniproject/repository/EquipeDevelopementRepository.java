package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.EquipeDevelopement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeDevelopementRepository extends JpaRepository<EquipeDevelopement, Long> {
}
