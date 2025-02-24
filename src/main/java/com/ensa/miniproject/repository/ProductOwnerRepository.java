package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.ProductOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner, Long> {
}
