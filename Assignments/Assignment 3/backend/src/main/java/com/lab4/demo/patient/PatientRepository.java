package com.lab4.demo.patient;

import com.lab4.demo.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Patient findPatientByName(String name);
}
