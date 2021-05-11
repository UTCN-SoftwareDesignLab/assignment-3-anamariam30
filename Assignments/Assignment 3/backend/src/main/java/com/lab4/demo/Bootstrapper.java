package com.lab4.demo;

import com.lab4.demo.consultation.ConsultationRepository;
import com.lab4.demo.patient.PatientRepository;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final PatientRepository patientRepository;
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final AuthService authService;



    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            consultationRepository.deleteAll();
            patientRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("ana@email.com")
                    .username("ana")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("ana1@email.com")
                    .username("ana1")
                    .password("WooHoo1!")
                    .roles(Set.of("SECRETARY"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("ana2@email.com")
                    .username("ana2")
                    .password("WooHoo1!")
                    .roles(Set.of("DOCTOR"))
                    .build());
        }
    }
}
