package com.burdettracker.budgedtrackerproject.model.dto.user;

import java.util.List;

public class AllUsersInfoDTO {

    private List<UserFullDetailsInfoDTO> allUsers;

    public AllUsersInfoDTO(List<UserFullDetailsInfoDTO> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserFullDetailsInfoDTO> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserFullDetailsInfoDTO> allUsers) {
        this.allUsers = allUsers;
    }
}
