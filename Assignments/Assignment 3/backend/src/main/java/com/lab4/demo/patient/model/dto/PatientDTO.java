package com.lab4.demo.patient.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;
    private String name;
    private String idc;
    private String pnc;
    private String address;
    private String date_of_birth;
}
