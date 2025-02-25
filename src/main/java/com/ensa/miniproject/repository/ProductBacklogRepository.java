package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, Long> {
    ProductBacklog getProductBacklogById(Long id);
}
