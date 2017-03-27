package com.tresbu.tresiot.service.mapper;

import com.tresbu.tresiot.domain.*;
import com.tresbu.tresiot.service.dto.EventdataDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Eventdata and its DTO EventdataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventdataMapper {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "applicationevent.id", target = "applicationeventId")
    @Mapping(source = "applicationview.id", target = "applicationviewId")
    @Mapping(source = "application.name", target = "applicationName")
    @Mapping(source = "applicationevent.name", target = "applicationeventName")
    @Mapping(source = "applicationview.name", target = "applicationviewName")
    EventdataDTO eventdataToEventdataDTO(Eventdata eventdata);

    List<EventdataDTO> eventdataToEventdataDTOs(List<Eventdata> eventdata);

    @Mapping(source = "applicationId", target = "application")
    @Mapping(source = "applicationeventId", target = "applicationevent")
    @Mapping(source = "applicationviewId", target = "applicationview")
    Eventdata eventdataDTOToEventdata(EventdataDTO eventdataDTO);

    List<Eventdata> eventdataDTOsToEventdata(List<EventdataDTO> eventdataDTOs);

    default Application applicationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }

    default Applicationevent applicationeventFromId(Long id) {
        if (id == null) {
            return null;
        }
        Applicationevent applicationevent = new Applicationevent();
        applicationevent.setId(id);
        return applicationevent;
    }

    default Applicationview applicationviewFromId(Long id) {
        if (id == null) {
            return null;
        }
        Applicationview applicationview = new Applicationview();
        applicationview.setId(id);
        return applicationview;
    }
}
