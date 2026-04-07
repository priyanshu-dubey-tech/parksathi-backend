package com.parksathi.repository;

import com.parksathi.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByIdAndDeletedFalse(Long id);

    Optional<Vehicle> findByIdAndUserIdAndDeletedFalseAndActiveTrue(
            Long id,
            Long userId
    );

    List<Vehicle> findByUserIdAndDeletedFalse(Long userId);

    Optional<Vehicle> findByUserIdAndVehicleNumberAndDeletedFalse(
            Long userId,
            String vehicleNumber
    );
}