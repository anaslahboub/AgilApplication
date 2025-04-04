package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.ScrumMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner, Long> {
    Optional<ProductOwner> findByUsername(String username);
}
