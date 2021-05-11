package com.lab4.demo;

import com.lab4.demo.consultation.model.Consultation;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.patient.model.Patient;
import com.lab4.demo.patient.model.dto.PatientDTO;
import com.lab4.demo.user.dto.UserListDTO;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Patient.class)) {
            supplier = TestCreationFactory::newPatient;
        } else if (cls.equals(PatientDTO.class)) {
            supplier = TestCreationFactory::newPatientDTO;
        } else if (cls.equals(String.class)) {
            supplier = TestCreationFactory::newPatientString;
        } else if (cls.equals(ConsultationDTO.class)) {
            supplier = TestCreationFactory::newConsultationDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }


    public static ConsultationDTO newConsultationDTO() {
        return ConsultationDTO.builder()
                .id(randomLong())
                .data(randomDate())
                .patient(randomString())
                .description(randomString())
                .doctor(randomString())
                .build();
    }

    private static String newPatientString() {

        return randomString();
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    public static Patient newPatient() {
        return Patient.builder()
                .id(randomLong())
                .name(randomString())
                .idc(randomString())
                .pnc(randomString())
                .address(randomString())
                .date_of_birth(randomDate())
                .build();
    }

    public static PatientDTO newPatientDTO() {
        return PatientDTO.builder()
                .id(randomLong())
                .name(randomString())
                .idc(randomString())
                .pnc(randomString())
                .address(randomString())
                .date_of_birth(String.valueOf(randomDate()))
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static Date randomDate() {
        return new Date(ThreadLocalRandom.current().nextInt() * 1000L);
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
