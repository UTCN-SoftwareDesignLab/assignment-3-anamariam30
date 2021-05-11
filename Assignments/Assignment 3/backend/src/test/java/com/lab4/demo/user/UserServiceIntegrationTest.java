package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.lab4.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username(randomString())
                    .password(randomString())
                    .email(randomEmail())
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getName());
        }
    }

    @Test
    void findDoctors()
    {

        String role = "SECRETARY";
        roleRepository.save(Role.builder().name(ERole.SECRETARY).build());
        UserDTO user = UserDTO.builder()
                .name(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles(role)
                .build();
        userService.create(user);
        String role1 = "DOCTOR";
        roleRepository.save(Role.builder().name(ERole.DOCTOR).build());
        UserDTO user1 = UserDTO.builder()
                .name(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles(role1)
                .build();
        userService.create(user1);

        Assertions.assertEquals(userService.allDoctorsForList().size(),1);

    }

    @Test
    void create(){
        String role = "SECRETARY";
        roleRepository.save(Role.builder().name(ERole.SECRETARY).build());
        UserDTO user = UserDTO.builder()
                .name(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles(role)
                .build();
        Assertions.assertNotNull(userService.create(user).getId());
    }
    @Test
    void delete(){
        String role = "DOCTOR";
        roleRepository.save(Role.builder().name(ERole.DOCTOR).build());
        UserDTO user = UserDTO.builder()
                .name(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles(role)
                .build();
        UserMinimalDTO user1 = userService.create(user);
        userService.delete(user1.getId());
    }

    @Test
    void edit(){
        String role = "SECRETARY";
        roleRepository.save(Role.builder().name(ERole.SECRETARY).build());
        UserDTO user = UserDTO.builder()
                .name(randomString())
                .password(randomString())
                .email(randomEmail())
                .roles(role)
                .build();
        UserMinimalDTO user1 = userService.create(user);
        String newName=randomString();
        user1.setName(newName);
        Assertions.assertEquals(userService.edit(user1.getId(),user1.getName()).getName(),newName);
    }
}