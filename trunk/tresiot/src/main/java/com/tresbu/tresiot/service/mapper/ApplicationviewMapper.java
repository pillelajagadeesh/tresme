package com.tresbu.tresiot.service.mapper;

import com.tresbu.tresiot.domain.*;
import com.tresbu.tresiot.service.dto.ApplicationviewDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Applicationview and its DTO ApplicationviewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationviewMapper {

    @Mapping(source = "application.id", target = "applicationId")
    ApplicationviewDTO applicationviewToApplicationviewDTO(Applicationview applicationview);

    List<ApplicationviewDTO> applicationviewsToApplicationviewDTOs(List<Applicationview> applicationviews);

    @Mapping(source = "applicationId", target = "application")
    Applicationview applicationviewDTOToApplicationview(ApplicationviewDTO applicationviewDTO);

    List<Applicationview> applicationviewDTOsToApplicationviews(List<ApplicationviewDTO> applicationviewDTOs);

    default Application applicationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
