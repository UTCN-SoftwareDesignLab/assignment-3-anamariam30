package com.lab4.demo.patient;

import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    private Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    public List<String> findAll() {
        return patientRepository.findAll().stream()
                .map(Patient::getName)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> findAllObj() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO create(PatientDTO patient) {
        return patientMapper.toDto(patientRepository.save(
                patientMapper.fromDto(patient)
        ));
    }

    public PatientDTO edit(PatientDTO patient) {
        Patient actPatient = findById(patient.getId());
        actPatient.setName(patient.getName());
        return patientMapper.toDto(
                patientRepository.save(actPatient)
        );
    }


}
