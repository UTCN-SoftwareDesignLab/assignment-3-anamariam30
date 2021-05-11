package com.lab4.demo.consultation;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.patient.PatientRepository;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.randomEmail;
import static com.lab4.demo.TestCreationFactory.randomString;

@SpringBootTest
class ConsultationServiceIntegrationTest {
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findAll() {

        Role roles = roleRepository.save(Role.builder().name(ERole.SECRETARY).build());
        User user =userRepository.save( User.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles((Set<Role>) roles)
                .build());
        Patient patient=patientRepository.save(TestCreationFactory.newPatient());
        ConsultationDTO consultationDTO=TestCreationFactory.newConsultationDTO();
        Consultation consultation=Consultation.builder()
                .id(consultationDTO.getId())
                .description(consultationDTO.getDescription())
                .data(consultationDTO.getData())
                .doctor(user)
                .patient(patient)
                .build();

        consultationRepository.save(consultation);
        List<ConsultationDTO> all = consultationService.findAll();
        Assertions.assertEquals(all.size(), 1);
    }

    @Test
    void findByDoctor() {
        Role roles = roleRepository.save(Role.builder().name(ERole.DOCTOR).build());
        User user =userRepository.save( User.builder()
                .username(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles((Set<Role>) roles)
                .build());
        Patient patient=patientRepository.save(TestCreationFactory.newPatient());
        ConsultationDTO consultationDTO=TestCreationFactory.newConsultationDTO();
        Consultation consultation=Consultation.builder()
                .id(consultationDTO.getId())
                .description(consultationDTO.getDescription())
                .data(consultationDTO.getData())
                .doctor(user)
                .patient(patient)
                .build();

        consultationRepository.save(consultation);
        List<ConsultationDTO> all = consultationService.findByDoctor(user);
        Assertions.assertEquals(all.size(), 1);
    }

    @Test
    void create() {
        ConsultationDTO consultation = TestCreationFactory.newConsultationDTO();
        ConsultationDTO createdConsultation = consultationService.create(consultation);
        consultation.setId(createdConsultation.getId());
        Assertions.assertEquals(createdConsultation, consultation);

    }

    @Test
    void edit() {
        ConsultationDTO newConsultation =TestCreationFactory.newConsultationDTO();
        ConsultationDTO consultation = consultationService.create(TestCreationFactory.newConsultationDTO());
        newConsultation.setId(consultation.getId());
        consultationService.edit(newConsultation);
        List<ConsultationDTO> consultations = consultationService.findAll();
        Assertions.assertTrue(consultations.contains(newConsultation));
        Assertions.assertFalse(consultations.contains(consultation));
    }

    @Test
    void delete() {
       ConsultationDTO consultationDTO=TestCreationFactory.newConsultationDTO();
       ConsultationDTO consultationDTO1 = consultationService.create(consultationDTO);
       consultationService.delete(consultationDTO1.getId());

    }
}