package com.parksathi.service.impl;

import com.parksathi.dto.request.VehicleRequest;
import com.parksathi.dto.response.VehicleResponse;
import com.parksathi.entity.User;
import com.parksathi.entity.Vehicle;
import com.parksathi.exception.VehicleNotFoundException;
import com.parksathi.mapper.VehicleMapper;
import com.parksathi.repository.UserRepository;
import com.parksathi.repository.VehicleRepository;
import com.parksathi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

        @Autowired
        private VehicleRepository repository;

        @Autowired
        private VehicleMapper mapper;

        @Autowired
        private UserRepository userRepository;

        private User getLoggedInUser() {
            String mobile = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByMobile(mobile)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        @Override
        public VehicleResponse addVehicle(VehicleRequest request) {
            User user = getLoggedInUser();

            Vehicle vehicle = mapper.toEntity(request);
            vehicle.setUserId(user.getId());
            vehicle.setVerified(false);
            vehicle.setDeleted(false);
            vehicle.setActive(true);

            return mapper.toResponse(repository.save(vehicle));
        }

        @Override
        public VehicleResponse getVehicle(Long id) {
            Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

            return mapper.toResponse(vehicle);
        }

        @Override
        public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
            User user = getLoggedInUser();

            Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

            if (!vehicle.getUserId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized access");
            }

            mapper.updateEntityFromRequest(request, vehicle);

            return mapper.toResponse(repository.save(vehicle));
        }

        @Override
        public String deleteVehicle(Long id) {
            User user = getLoggedInUser();

            Vehicle vehicle = repository.findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

            if (!vehicle.getUserId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized access");
            }

            vehicle.setDeleted(true);
            vehicle.setActive(false);

            repository.save(vehicle);

            return "Vehicle deleted successfully";
        }

    @Override
    public List<VehicleResponse> getMyVehicles() {

        User user = getLoggedInUser();

        return repository.findByUserIdAndDeletedFalse(user.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public VehicleResponse getMyVehicleByNumber(String vehicleNumber) {

        User user = getLoggedInUser();

        Vehicle vehicle = repository
                .findByUserIdAndVehicleNumberAndDeletedFalse(
                        user.getId(),
                        vehicleNumber
                )
                .orElseThrow(() ->
                        new VehicleNotFoundException("Vehicle not found"));

        return mapper.toResponse(vehicle);
    }

    }