package com.parksathi.mapper;

import com.parksathi.dto.request.VehicleRequest;
import com.parksathi.dto.response.VehicleResponse;
import com.parksathi.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleRequest request);

    VehicleResponse toResponse(Vehicle vehicle);

    void updateEntityFromRequest(VehicleRequest request, @MappingTarget Vehicle vehicle);
}
