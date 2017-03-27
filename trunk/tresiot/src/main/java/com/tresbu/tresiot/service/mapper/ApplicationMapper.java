package com.tresbu.tresiot.service.mapper;

import com.tresbu.tresiot.domain.*;
import com.tresbu.tresiot.service.dto.ApplicationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Application and its DTO ApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationMapper {

    ApplicationDTO applicationToApplicationDTO(Application application);

    List<ApplicationDTO> applicationsToApplicationDTOs(List<Application> applications);

    Application applicationDTOToApplication(ApplicationDTO applicationDTO);

    List<Application> applicationDTOsToApplications(List<ApplicationDTO> applicationDTOs);
}
