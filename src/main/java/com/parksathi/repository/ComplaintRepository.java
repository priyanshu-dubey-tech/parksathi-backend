package com.parksathi.repository;

import com.parksathi.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findBySessionTokenAndDeletedFalse(String sessionToken);
}