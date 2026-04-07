package com.parksathi.controller;

import com.parksathi.dto.request.OwnerResponseRequest;
import com.parksathi.dto.request.QrScanRequest;
import com.parksathi.dto.response.ContactSessionResponse;
import com.parksathi.dto.response.OwnerResponseAck;
import com.parksathi.dto.response.ScanPageResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import com.parksathi.service.ContactSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactSessionController {

    @Autowired
    private ContactSessionService service;

    @PostMapping("/scan")
    public ContactSessionResponse scanQr(
            @RequestBody QrScanRequest request
    ) {
        return service.createSession(request);
    }

    @PostMapping("/call/{sessionToken}")
    public String callOwner(@PathVariable String sessionToken) {
        return service.callOwner(sessionToken);
    }

    @PostMapping("/alert/{sessionToken}")
    public String sendAlert(@PathVariable String sessionToken) {
        return service.sendAlert(sessionToken);
    }

    @GetMapping("/complaint/check/{sessionToken}")
    public Boolean canRaiseComplaint(@PathVariable String sessionToken) {
        return service.canRaiseComplaint(sessionToken);
    }

    @PostMapping("/owner/respond")
    public OwnerResponseAck ownerRespond(
            @RequestBody OwnerResponseRequest request
    ) {
        return service.ownerRespond(request);
    }

    @GetMapping("/scan-page")
    public ScanPageResponse getScanPage(
            @RequestParam String token
    ) {
        return service.getScanPage(token);
    }

    @GetMapping("/history/owner")
    public List<SessionHistoryResponse> getOwnerHistory() {
        return service.getOwnerHistory();
    }


    @GetMapping("/history/victim")
    public List<SessionHistoryResponse> getVictimHistory() {
        return service.getVictimHistory();
    }
}
