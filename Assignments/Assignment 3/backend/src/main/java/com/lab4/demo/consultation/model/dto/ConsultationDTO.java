package com.lab4.demo.consultation.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {

    private Long id;
    private String patient;
    private String doctor;
    private Date data;
    private String description;
}
