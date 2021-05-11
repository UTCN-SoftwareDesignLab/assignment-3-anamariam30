package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ConsultationMapper {
    @Mapping(source="patient.name",target = "patient")
    @Mapping(source="doctor.username",target = "doctor")
    ConsultationDTO toDto(Consultation consultation);
    @Mapping(target="patient.name",source = "patient")
    @Mapping(target="doctor.username",source = "doctor")
    @Mapping(target="data",dateFormat = "yyyy-MM-dd HH:mm")
    Consultation fromDto(ConsultationDTO consultation);
}
