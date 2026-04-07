package com.parksathi.controller;

import com.parksathi.dto.request.VehicleRequest;
import com.parksathi.dto.response.VehicleResponse;
import com.parksathi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @PostMapping("/add")
    public VehicleResponse addVehicle(@RequestBody VehicleRequest request) {
        return service.addVehicle(request);
    }

    @GetMapping("/{id}")
    public VehicleResponse getVehicle(@PathVariable Long id) {
        return service.getVehicle(id);
    }

    @PutMapping("/{id}")
    public VehicleResponse updateVehicle(@PathVariable Long id,
                                         @RequestBody VehicleRequest request) {
        return service.updateVehicle(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        return service.deleteVehicle(id);
    }

    @GetMapping("/my")
    public List<VehicleResponse> getMyVehicles() {
        return service.getMyVehicles();
    }


    @GetMapping("/my/{vehicleNumber}")
    public VehicleResponse getMyVehicleByNumber(
            @PathVariable String vehicleNumber
    ) {
        return service.getMyVehicleByNumber(vehicleNumber);
    }
}