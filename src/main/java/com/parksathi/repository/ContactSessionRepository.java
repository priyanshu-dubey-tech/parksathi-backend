package com.parksathi.repository;

import com.parksathi.entity.ContactSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactSessionRepository
        extends JpaRepository<ContactSession, Long> {

    Optional<ContactSession> findBySessionToken(String sessionToken);

    Optional<ContactSession>
    findBySessionTokenAndOwnerUserId(String sessionToken, Long ownerUserId);

    List<ContactSession> findByOwnerUserIdOrderByCreatedAtDesc(Long ownerUserId);

    List<ContactSession> findByScannerMobileOrderByCreatedAtDesc(String scannerMobile);
}