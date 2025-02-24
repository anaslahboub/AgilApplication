package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.ScrumMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrumMasterRepository extends JpaRepository<ScrumMaster, Long> {
}
