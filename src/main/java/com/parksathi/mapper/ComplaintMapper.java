package com.parksathi.mapper;

import com.parksathi.dto.response.ComplaintResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import com.parksathi.entity.Complaint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    ComplaintResponse toResponse(Complaint complaint);
    SessionHistoryResponse toSessionHistoryResponse(Complaint complaint);
}