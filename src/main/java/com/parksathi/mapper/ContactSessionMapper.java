package com.parksathi.mapper;

import com.parksathi.dto.response.ContactSessionResponse;
import com.parksathi.dto.response.SessionHistoryResponse;
import com.parksathi.entity.ContactSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactSessionMapper {

    @Mapping(target = "ownerName", source = "ownerName")
    @Mapping(target = "sessionToken", source = "session.sessionToken")
    @Mapping(target = "expiresAt", source = "session.expiresAt")
    @Mapping(target = "status", source = "session.status")
    @Mapping(target = "complaintAllowed", constant = "false")
    ContactSessionResponse toResponse(ContactSession session, String ownerName);

    SessionHistoryResponse toHistoryResponse(ContactSession session);
}