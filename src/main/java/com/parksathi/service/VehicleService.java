package com.parksathi.service;


import com.parksathi.dto.request.VehicleRequest;
import com.parksathi.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

    VehicleResponse addVehicle(VehicleRequest request);

    VehicleResponse getVehicle(Long id);

    VehicleResponse updateVehicle(Long id, VehicleRequest request);

    String deleteVehicle(Long id);

    List<VehicleResponse> getMyVehicles();

    VehicleResponse getMyVehicleByNumber(String vehicleNumber);
}