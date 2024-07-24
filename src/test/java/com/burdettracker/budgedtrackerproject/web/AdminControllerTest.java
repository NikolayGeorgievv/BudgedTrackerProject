package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.user.UserChangeInformationDTO;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.burdettracker.budgedtrackerproject.utils.TestUtils.createDummyUser;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "myEmail@example.com", roles = {"ADMIN"})
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminController adminController;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void getAdminPage() throws Exception {
        User user = createDummyUser(rolesRepository);
        userRepository.saveAndFlush(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/adminPage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/adminPage"));
    }
    @Test
    void filterUsersByEmail() throws Exception {
        User user = createDummyUser(rolesRepository);
        userRepository.saveAndFlush(user);

        String email = "test@example.com";
        mockMvc.perform(MockMvcRequestBuilders.get("/filterUsers").param("emailFilter", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("adminPage"));


    }

    @Test
    void getUserFullInformation() throws Exception {
        User user = createDummyUser(rolesRepository);
        User savedUser = userRepository.saveAndFlush(user);


        String userId = String.valueOf(savedUser.getId());

        assertTrue(userRepository.findById(UUID.fromString(userId)).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/allUsers/" + userId + "/editUser"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("editUserPage"));
    }

    @Test
    void editUser() {
        User user = createDummyUser(rolesRepository);
        User savedUser = userRepository.saveAndFlush(user);
        UserChangeInformationDTO userChangeInformationDTO = new UserChangeInformationDTO();
        userChangeInformationDTO.setId(String.valueOf(savedUser.getId()));
        userChangeInformationDTO.setNewFirstName("newFirstName");
        userChangeInformationDTO.setNewLastName("newLastName");
        userChangeInformationDTO.setNewPhoneNumber("123456789");
        userChangeInformationDTO.setMembership("PREMIUM");

        String userId = String.valueOf(user.getId());
        String dtoId = userChangeInformationDTO.getId();

        adminController.editUser(userChangeInformationDTO);

        Optional<User> updatedUserOpt = userRepository.findById(savedUser.getId());

        assertTrue(updatedUserOpt.isPresent());

        User updatedUser = updatedUserOpt.get();

        assertEquals("newFirstName", updatedUser.getFirstName());
        assertEquals("newLastName", updatedUser.getLastName());
        assertEquals("123456789", updatedUser.getPhoneNumber());
        assertEquals(MembershipType.PREMIUM, updatedUser.getMembershipType());
    }
}