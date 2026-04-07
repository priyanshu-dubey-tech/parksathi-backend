package com.parksathi.controller;

import com.parksathi.dto.request.ComplaintRequest;
import com.parksathi.dto.response.ComplaintResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import com.parksathi.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService service;

    @PostMapping("/raise")
    public SessionHistoryResponse raiseComplaint(
            @RequestPart("data") ComplaintRequest request,
            @RequestPart("file") MultipartFile file
    ) {
        return service.raiseComplaint(request, file);
    }
}