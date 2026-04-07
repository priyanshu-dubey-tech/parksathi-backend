package com.parksathi.service;

import com.parksathi.dto.response.QrResponse;

public interface QrService {

    QrResponse generateQr(Long vehicleId);
}