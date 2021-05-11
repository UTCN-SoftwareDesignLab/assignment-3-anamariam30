package com.lab4.demo.patient;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.PatientRepository;
import com.lab4.demo.patient.PatientService;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;


    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        patientRepository.saveAll(patients);
        List<String> all = patientService.findAll();
        Assertions.assertEquals(patients.size(), all.size());
    }

    @Test
    void create() {
        PatientDTO patient = TestCreationFactory.newPatientDTO();
        PatientDTO createdPatient = patientService.create(patient);
        patient.setId(createdPatient.getId());
        Assertions.assertEquals(createdPatient, patient);
    }

    @Test
    void edit() {

        PatientDTO newPatient =TestCreationFactory.newPatientDTO();
        PatientDTO patient = patientService.create(TestCreationFactory.newPatientDTO());
        newPatient.setId(patient.getId());
        patientService.edit(newPatient);
        List<String> books = patientService.findAll();
        Assertions.assertTrue(books.contains(newPatient));
        Assertions.assertFalse(books.contains(patient));
    }
}