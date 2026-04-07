package com.parksathi.service.impl;

import com.parksathi.dto.request.OwnerResponseRequest;
import com.parksathi.dto.request.QrScanRequest;
import com.parksathi.dto.response.ContactSessionResponse;
import com.parksathi.dto.response.OwnerResponseAck;
import com.parksathi.dto.response.ScanPageResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import com.parksathi.dto.websocket.ContactSocketMessage;
import com.parksathi.entity.ContactSession;
import com.parksathi.mapper.ContactSessionMapper;
import com.parksathi.repository.ContactSessionRepository;
import com.parksathi.service.ContactSessionService;
import com.parksathi.entity.User;
import com.parksathi.entity.Vehicle;
import com.parksathi.repository.UserRepository;
import com.parksathi.repository.VehicleRepository;
import com.parksathi.util.QrTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ContactSessionServiceImpl implements ContactSessionService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactSessionRepository repository;

    @Autowired
    private ContactSessionMapper contactSessionMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private User getLoggedInUser() {
        String mobile = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public ContactSessionResponse createSession(QrScanRequest request) {

        Long vehicleId = Long.valueOf(
                QrTokenUtil.decrypt(request.getQrToken())
        );

        Vehicle vehicle = vehicleRepository.findByIdAndDeletedFalse(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        User owner = userRepository.findById(vehicle.getUserId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        ContactSession session = new ContactSession();
        session.setVehicleId(vehicle.getId());
        session.setOwnerUserId(owner.getId());
        session.setScannerMobile(request.getScannerMobile());
        session.setReason(request.getReason());
        session.setSessionToken(UUID.randomUUID().toString());
        session.setStatus("ACTIVE");
        session.setExpiresAt(expiry);
        session.setComplaintAllowed(false);

        ContactSession savedSession = repository.save(session);

        return contactSessionMapper.toResponse(
                savedSession,
                owner.getFirstName()
        );
    }

    @Override
    public String callOwner(String sessionToken) {
        ContactSession session = repository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setCallAttempted(true);
        session.setLastAttemptedAt(LocalDateTime.now());
        session.setStatus("CALL_ATTEMPTED");

        repository.save(session);

        return "Masked call initiated to vehicle owner";
    }

    @Override
    public String sendAlert(String sessionToken) {
        ContactSession session = repository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setAlterSent(true);
        session.setLastAttemptedAt(LocalDateTime.now());
        session.setStatus("ALERT_SENT");

        repository.save(session);

// future push notification integration
        return "Move vehicle alert sent to owner mobile";
    }

    @Override
    public Boolean canRaiseComplaint(String sessionToken) {
        ContactSession session = repository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getLastAttemptedAt() == null) {
            return false;
        }

        boolean timeoutPassed =
                session.getLastAttemptedAt()
                        .plusMinutes(2)
                        .isBefore(LocalDateTime.now());

        if (timeoutPassed) {
            session.setComplaintAllowed(true);
            session.setStatus("NO_RESPONSE");
            repository.save(session);
            return true;
        }

        return false;
    }
    @Override
    public OwnerResponseAck ownerRespond(OwnerResponseRequest request) {

        User owner = getLoggedInUser();

        ContactSession session = repository
                .findBySessionTokenAndOwnerUserId(
                        request.getSessionToken(),
                        owner.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Unauthorized session access"));

        session.setOwnerResponse(request.getResponseType());
        session.setOwnerRespondedAt(LocalDateTime.now());
        session.setStatus("OWNER_RESPONDED");

        repository.save(session);

        messagingTemplate.convertAndSendToUser(
                session.getScannerMobile(), // victim identifier
                "/queue/contact",
                new ContactSocketMessage(
                        "OWNER_RESPONSE",
                        request.getResponseType()
                )
        );
        return new OwnerResponseAck(
                "Owner response recorded successfully",
                request.getResponseType()
        );
    }
    @Override
    public ScanPageResponse getScanPage(String token) {

        Long vehicleId = Long.valueOf(
                QrTokenUtil.decrypt(token)
        );

        Vehicle vehicle = vehicleRepository
                .findByIdAndDeletedFalse(vehicleId)
                .orElseThrow(() ->
                        new RuntimeException("Invalid QR token"));

        User owner = userRepository.findById(vehicle.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Owner not found"));

        return new ScanPageResponse(
                owner.getFirstName(),
                vehicle.getVehicleNumber(),
                true,
                "Vehicle owner found. Continue to contact."
        );

    }

    @Override
    public List<SessionHistoryResponse> getOwnerHistory() {

        User owner = getLoggedInUser();

        return repository.findByOwnerUserIdOrderByCreatedAtDesc(owner.getId())
                .stream()
                .map(session -> {
                    SessionHistoryResponse response =
                            contactSessionMapper.toHistoryResponse(session);

                    String vehicleNumber = vehicleRepository
                            .findById(session.getVehicleId())
                            .map(Vehicle::getVehicleNumber)
                            .orElse(null);

                    response.setVehicleNumber(vehicleNumber);

                    return response;
                })
                .toList();
    }

    @Override
    public List<SessionHistoryResponse> getVictimHistory() {

        User user = getLoggedInUser();

        return repository.findByScannerMobileOrderByCreatedAtDesc(
                        user.getMobile()
                )
                .stream()
                .map(session -> {
                    SessionHistoryResponse response =
                            contactSessionMapper.toHistoryResponse(session);

                    String vehicleNumber = vehicleRepository
                            .findById(session.getVehicleId())
                            .map(Vehicle::getVehicleNumber)
                            .orElse(null);

                    response.setVehicleNumber(vehicleNumber);

                    return response;
                })
                .toList();
    }

}