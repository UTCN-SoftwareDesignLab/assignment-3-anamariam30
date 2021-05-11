package com.lab4.demo.patient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(length = 512, nullable = false)
    private String idc;

    @Column(length = 512, nullable = false)
    private String pnc;

    @Column(length = 512, nullable = false)
    private String address;

    @Column
    private Date date_of_birth;





}
