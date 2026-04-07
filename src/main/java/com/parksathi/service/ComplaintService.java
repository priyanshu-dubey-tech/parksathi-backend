package com.parksathi.service;

import com.parksathi.dto.request.ComplaintRequest;
import com.parksathi.dto.response.ComplaintResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ComplaintService {

    SessionHistoryResponse raiseComplaint(
            ComplaintRequest request,
            MultipartFile image
    );
}