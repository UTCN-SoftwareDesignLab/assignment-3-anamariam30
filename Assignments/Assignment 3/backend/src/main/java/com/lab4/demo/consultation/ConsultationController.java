package com.lab4.demo.consultation;

import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.CONSULTATION;
import static com.lab4.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(CONSULTATION)
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;
    private final UserService userService;


    @GetMapping
    public List<ConsultationDTO> allConsultations() {
        List<ConsultationDTO> all = consultationService.findAll();
        return all;
    }

    @GetMapping(ENTITY)
    public List<ConsultationDTO> allConsultationsByDoctor(@PathVariable @NonNull Long id) {
        List<ConsultationDTO> all = consultationService.findByDoctor(userService.findById(id));
        return all;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ConsultationDTO consultation) {
        if (consultationService.doctorAvailability(consultation.getDoctor(), consultation.getData())) {
            ConsultationDTO consultationDTO = consultationService.create(consultation);
            return new ResponseEntity<>(consultationDTO, HttpStatus.ACCEPTED);
        } else {

            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Doctor is not available!"));
        }

    }

    @PatchMapping
    public ConsultationDTO edit(@RequestBody ConsultationDTO consultation) {
        return consultationService.edit(consultation);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable @NonNull Long id) {
        consultationService.delete(id);
    }


}
