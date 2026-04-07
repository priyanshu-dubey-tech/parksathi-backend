package com.parksathi.service.impl;

import com.parksathi.dto.response.QrResponse;
import com.parksathi.entity.User;
import com.parksathi.entity.Vehicle;
import com.parksathi.entity.VehicleQr;
import com.parksathi.repository.UserRepository;
import com.parksathi.repository.VehicleQrRepository;
import com.parksathi.repository.VehicleRepository;
import com.parksathi.service.QrService;
import com.parksathi.util.QrImageUtil;
import com.parksathi.util.QrTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QrServiceImpl implements QrService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleQrRepository qrRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private User getLoggedInUser() {
        String mobile = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public QrResponse generateQr(Long vehicleId) {
        User user = getLoggedInUser();

        Vehicle vehicle = vehicleRepository
                .findByIdAndUserIdAndDeletedFalseAndActiveTrue(
                        vehicleId,
                        user.getId()
                )
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        //String token = QrTokenUtil.encrypt(vehicle.getId().toString());

        String token = QrTokenUtil.encrypt(vehicle.getId().toString());
        String qrUrl = frontendUrl+"/scan?token=" + token;

        VehicleQr qr = new VehicleQr();
        qr.setVehicleId(vehicle.getId());
        qr.setQrToken(qrUrl);

        qrRepository.save(qr);

        String qrBase64 = QrImageUtil.generateBase64Qr(qrUrl);

        return new QrResponse(
                vehicle.getId(),
                qrUrl,
                qrBase64
        );
    }
}