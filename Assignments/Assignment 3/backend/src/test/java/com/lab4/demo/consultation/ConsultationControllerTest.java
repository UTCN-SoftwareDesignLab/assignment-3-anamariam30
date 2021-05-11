package com.lab4.demo.consultation;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.consultation.model.dto.ConsultationDTO;
import com.lab4.demo.patient.PatientController;
import com.lab4.demo.patient.PatientService;
import com.lab4.demo.patient.model.dto.PatientDTO;
import com.lab4.demo.user.RoleRepository;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultationControllerTest extends BaseControllerTest {

    @InjectMocks
    private ConsultationController consultationController;

    @Mock
    private ConsultationService consultationService;

    @Mock
    private UserService userService;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        consultationController =new ConsultationController(consultationService,userService);
        mockMvc = MockMvcBuilders.standaloneSetup(consultationController).build();
    }

    @Test
    void allConsultations() throws Exception {
        List<ConsultationDTO> consultationDTOS = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findAll()).thenReturn(consultationDTOS);

        ResultActions result = mockMvc.perform(get(CONSULTATION));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTOS));
    }

    @Test
    void allConsultationsByDoctor() throws Exception {
        String role1 = "DOCTOR";
        Role role=roleRepository.save(Role.builder().name(ERole.DOCTOR).build());
        String username = randomString();
        long id = randomLong();
        User user = User.builder()
                .id(id)
                .username(username)
                .password(randomString())
                .email(randomEmail())
                .roles((Set<Role>) role)
                .build();

        List<ConsultationDTO> consultationDTOS = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findByDoctor(user)).thenReturn(consultationDTOS);

        ResultActions result = mockMvc.perform(get(CONSULTATION+ENTITY,id));
        result.andExpect(status().isOk()).andExpect(jsonContentToBe(consultationDTOS));

    }

    @Test
    void create() throws Exception {

        ConsultationDTO consultationDTO = TestCreationFactory.newConsultationDTO();

        when(consultationService.create(consultationDTO)).thenReturn(consultationDTO);
        ResultActions result = performPostWithRequestBody(
                CONSULTATION,consultationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));

    }

    @Test
    void edit() throws Exception {
        ConsultationDTO consultationDTO = TestCreationFactory.newConsultationDTO();

        when(consultationService.edit(consultationDTO)).thenReturn(consultationDTO);
        System.out.println(consultationService.edit(consultationDTO));
        ResultActions result = performPatchWithRequestBodyAndPathVariable(CONSULTATION,consultationDTO.getId().toString(),consultationDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTO));

    }

    @Test
    void delete() throws Exception {
        ConsultationDTO consultationDTO = TestCreationFactory.newConsultationDTO();
        ResultActions result = performDeleteWithPathVariable(CONSULTATION+ENTITY,consultationDTO.getId().toString());
        result.andExpect(status().isOk());

    }
}