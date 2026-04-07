package com.parksathi.service.impl;

import com.parksathi.dto.request.ComplaintRequest;
import com.parksathi.dto.response.ComplaintResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import com.parksathi.entity.Complaint;
import com.parksathi.entity.ContactSession;
import com.parksathi.entity.User;
import com.parksathi.exception.ComplaintException;
import com.parksathi.mapper.ComplaintMapper;
import com.parksathi.repository.ComplaintRepository;
import com.parksathi.repository.ContactSessionRepository;
import com.parksathi.repository.UserRepository;
import com.parksathi.service.ComplaintService;
import com.parksathi.util.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ContactSessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintMapper mapper;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    private User getLoggedInUser() {
        String mobile = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public SessionHistoryResponse raiseComplaint(
            ComplaintRequest request,
            MultipartFile image
    ) {

        User victim = getLoggedInUser();

// Validate session
        ContactSession session = sessionRepository
                .findBySessionToken(request.getSessionToken())
                .orElseThrow(() -> new ComplaintException("Invalid session"));

//  Check ownership
        if (!session.getScannerMobile().equals(victim.getMobile())) {
            throw new ComplaintException("Unauthorized complaint");
        }

//  Check timeout condition
        if (!Boolean.TRUE.equals(session.getComplaintAllowed())) {
            throw new ComplaintException("Complaint not allowed yet");
        }

// Prevent duplicate
        if (complaintRepository
                .findBySessionTokenAndDeletedFalse(request.getSessionToken())
                .isPresent()) {
            throw new ComplaintException("Complaint already raised");
        }

//  Upload image
        String imageUrl = fileStorageUtil.saveFile(image);

// Create complaint
        Complaint complaint = new Complaint();
        complaint.setVehicleId(session.getVehicleId());
        complaint.setOwnerUserId(session.getOwnerUserId());
        complaint.setVictimUserId(victim.getId());
        complaint.setSessionToken(session.getSessionToken());
        complaint.setReason(request.getReason());
        complaint.setImageUrl(imageUrl);
        complaint.setLatitude(request.getLatitude());
        complaint.setLongitude(request.getLongitude());
        complaint.setAddress(request.getAddress());
        complaint.setStatus("CREATED");
        complaint.setCreatedAt(LocalDateTime.now());

        Complaint saved = complaintRepository.save(complaint);

        return mapper.toSessionHistoryResponse(saved);
    }
}