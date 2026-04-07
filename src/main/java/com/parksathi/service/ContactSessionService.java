package com.parksathi.service;

import com.parksathi.dto.request.OwnerResponseRequest;
import com.parksathi.dto.request.QrScanRequest;
import com.parksathi.dto.response.ContactSessionResponse;
import com.parksathi.dto.response.OwnerResponseAck;
import com.parksathi.dto.response.ScanPageResponse;
import com.parksathi.dto.response.SessionHistoryResponse;

import java.util.List;

public interface ContactSessionService {

    ContactSessionResponse createSession(QrScanRequest request);

    String callOwner(String sessionToken);

    String sendAlert(String sessionToken);

    Boolean canRaiseComplaint(String sessionToken);

    OwnerResponseAck ownerRespond(OwnerResponseRequest request);

    ScanPageResponse getScanPage(String token);

    List<SessionHistoryResponse> getOwnerHistory();

    List<SessionHistoryResponse> getVictimHistory();
}