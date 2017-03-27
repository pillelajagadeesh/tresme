package com.tresbu.tresiot.service.mapper;

import com.tresbu.tresiot.domain.*;
import com.tresbu.tresiot.service.dto.ApplicationeventDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Applicationevent and its DTO ApplicationeventDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationeventMapper {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "application.name", target = "applicationName")
    ApplicationeventDTO applicationeventToApplicationeventDTO(Applicationevent applicationevent);

   
    List<ApplicationeventDTO> applicationeventsToApplicationeventDTOs(List<Applicationevent> applicationevents);

    @Mapping(source = "applicationId", target = "application")
    Applicationevent applicationeventDTOToApplicationevent(ApplicationeventDTO applicationeventDTO);

    List<Applicationevent> applicationeventDTOsToApplicationevents(List<ApplicationeventDTO> applicationeventDTOs);

    default Application applicationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
