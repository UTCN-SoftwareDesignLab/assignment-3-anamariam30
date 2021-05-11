package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
     List<Consultation> findConsultationsByDoctor(User doctor);
     List<Consultation> findConsultationsByDoctorAndAndData(User doctor, Date data);

}
