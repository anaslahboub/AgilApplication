package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.ScrumMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrumMasterRepository extends JpaRepository<ScrumMaster, Long> {
    List<ScrumMaster> getScrumMastersById(Long id);
}
