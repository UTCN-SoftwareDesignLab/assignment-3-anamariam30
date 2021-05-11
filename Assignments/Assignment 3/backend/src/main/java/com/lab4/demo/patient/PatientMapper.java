package com.lab4.demo.patient;

import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(Patient patient);
    @Mapping(target="date_of_birth",dateFormat = "yyyy-MM-dd")
    Patient fromDto(PatientDTO patient);
}
