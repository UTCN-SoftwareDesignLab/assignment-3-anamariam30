package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.patient.PatientRepository;
import com.lab4.demo.patient.model.Patient;

import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final ConsultationMapper consultationMapper;

    private Consultation findById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }

    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ConsultationDTO> findByDoctor(User doctor) {
        return consultationRepository.findConsultationsByDoctor(doctor).stream()
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }
    public ConsultationDTO create(ConsultationDTO consultation) {
        Consultation cons=consultationMapper.fromDto(consultation);
        User doctor=userRepository.findByUsername(consultation.getDoctor()).get();
        Patient patient=patientRepository.findPatientByName(consultation.getPatient());
        cons.setDoctor(doctor);
        cons.setPatient(patient);
        Consultation save = consultationRepository.save(cons);
        ConsultationDTO consultationDTO = consultationMapper.toDto(save);
        return consultationDTO;
    }


    public ConsultationDTO edit(ConsultationDTO consultation) {
        Consultation actConsultation = findById(consultation.getId());
        actConsultation.setDescription(consultation.getDescription());
        actConsultation.setDoctor(userRepository.findByUsername(consultation.getDoctor()).get());
        return consultationMapper.toDto(
                consultationRepository.save(actConsultation)
        );
    }

    public void delete(Long id) {
        Optional<Consultation> consultation = consultationRepository.findById(id);
        consultationRepository.delete(consultation.get());
    }


    public Boolean doctorAvailability(String name, Date data){
        User doctor= userRepository.findByUsername(name).get();
        List<Consultation> consultations=consultationRepository.findConsultationsByDoctorAndAndData(doctor,data);
        return consultations.isEmpty();
    }


}
