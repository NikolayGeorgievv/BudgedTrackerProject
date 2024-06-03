package com.burdettracker.budgedtrackerproject.web;

import com.burdettracker.budgedtrackerproject.model.dto.membership.ChangeMembershipDTO;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.model.entity.UserRoleEntity;
import com.burdettracker.budgedtrackerproject.model.entity.enums.MembershipType;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("myEmail@example.com")
class MainPageControllerTest {

    @Autowired
    private MainPageController mainPageController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;


    User user;

    @BeforeEach
    public void setUp() {
        user = createDummyUser();
        userRepository.saveAndFlush(user);
    }

    @Test
    void loggedIn() {
        String viewName = mainPageController.loggedIn(model);
        Assertions.assertEquals("redirect:/homePage", viewName);
    }

    @Test
    void changeMembershipPlan() {
        ChangeMembershipDTO changeMembershipDTO = new ChangeMembershipDTO();
        changeMembershipDTO.setAccountToUse("MyAccount");
        changeMembershipDTO.setMembership("FREE");
        String userEmail = user.getEmail();
        User user = userRepository.getByEmail(userEmail);
        doAnswer(invocation ->{


            user.setAccountNameAssignedForSubscription(changeMembershipDTO.getAccountToUse());
            changeUserMembership(user, changeMembershipDTO.getMembership());
            return null;
        }).when(userService).changeUserPlan(changeMembershipDTO, userEmail);

        mainPageController.changeMembershipPlan(changeMembershipDTO, bindingResult);
        User updatedUser = userRepository.getByEmail(user.getEmail());



        Assertions.assertEquals("FREE", updatedUser.getMembershipType().toString());
    }

    @Test
    void getUserByEmail() {
        when(userService.getUserByEmail("myEmail@example.com")).thenReturn(user);
    }


    private User createDummyUser() {
        UUID uuid = new UUID(5, 10);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.ADMIN);
        User user = new User(uuid,
                MembershipType.PREMIUM,
                "firstName",
                "lastName",
                "myEmail@example.com",
                "phoneNumber",
                "test",
                10,
                LocalDate.now(),
                "MyAccount",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                List.of(userRoleEntity));
        rolesRepository.saveAllAndFlush(List.of(userRoleEntity));

        return user;
    }
    private void changeUserMembership(User user, String membership) {
        switch (membership) {
            case "FREE":
                user.setMembershipType(MembershipType.FREE);
                user.setUserAccountsAllowed(1);
                break;
            case "GOLD":
                user.setMembershipType(MembershipType.GOLD);
                user.setUserAccountsAllowed(2);
                break;
            case "PREMIUM":
                user.setMembershipType(MembershipType.PREMIUM);
                user.setUserAccountsAllowed(10);
                break;
        }
    }
}