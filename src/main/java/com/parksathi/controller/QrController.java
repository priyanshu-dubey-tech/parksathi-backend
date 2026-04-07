package com.parksathi.controller;

import com.parksathi.dto.response.QrResponse;
import com.parksathi.service.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QrController {

    @Autowired
    private QrService service;

    @PostMapping("/generate/{vehicleId}")
    public QrResponse generateQr(@PathVariable Long vehicleId) {
        return service.generateQr(vehicleId);
    }
}