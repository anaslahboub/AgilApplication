package com.ensa.miniproject.repository;

import com.ensa.miniproject.entities.UserStoryClone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryCloneRepository extends JpaRepository<UserStoryClone, Long> {
}