package com.lab4.demo.patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.lab4.demo.UrlMapping.FRONT_OFFICE;
import static com.lab4.demo.UrlMapping.OBJ;

@RestController
@RequestMapping(FRONT_OFFICE)
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    public List<String> allPatient() {
        List<String> all = patientService.findAll();
        return all;
    }

    @GetMapping(OBJ)
    public List<PatientDTO> allPatients() {
        List<PatientDTO> all = patientService.findAllObj();
        return all;
    }

    @PostMapping
    public PatientDTO create(@RequestBody PatientDTO patient) {
        return patientService.create(patient);
    }

    @PatchMapping
    public PatientDTO edit(@RequestBody PatientDTO patient) {
        return patientService.edit(patient);
    }


}
