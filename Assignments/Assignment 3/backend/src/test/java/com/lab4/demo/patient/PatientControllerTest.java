package com.lab4.demo.patient;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.patient.model.dto.PatientDTO;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.user.UserController;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest extends BaseControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        patientController =new PatientController(patientService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void allPatient() throws Exception {
        List<String> patientListStrings = TestCreationFactory.listOf(String.class);
        when(patientService.findAll()).thenReturn(patientListStrings);

        ResultActions result = mockMvc.perform(get(FRONT_OFFICE));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientListStrings));
    }

    @Test
    void allPatients() throws Exception {
        List<PatientDTO> patientListDto = TestCreationFactory.listOf(PatientDTO.class);
        when(patientService.findAllObj()).thenReturn(patientListDto);

        ResultActions result = mockMvc.perform(get(FRONT_OFFICE+"/obj"));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientListDto));
    }

    @Test
    void create() throws Exception {
        String date_of_birth = String.valueOf(randomDate());
        PatientDTO patient = PatientDTO.builder()
                .name(randomString())
                .idc(randomString())
                .pnc(randomString())
                .address(randomString())
                .date_of_birth(date_of_birth)
                .build();

         when(patientService.create(patient)).thenReturn(patient);
        ResultActions result = performPostWithRequestBody(FRONT_OFFICE,patient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));

    }

    @Test
    void edit() throws Exception {
        String date_of_birth = String.valueOf(randomDate());
        long id = randomLong();
        PatientDTO patient = PatientDTO.builder()
                .id(id)
                .name(randomString())
                .idc(randomString())
                .pnc(randomString())
                .address(randomString())
                .date_of_birth(date_of_birth)
                .build();


        when(patientService.edit(patient)).thenReturn(patient);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(FRONT_OFFICE+ENTITY,patient.getId().toString(),patient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patient));
    }
}