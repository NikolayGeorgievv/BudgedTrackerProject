package com.burdettracker.budgedtrackerproject.model.entity;

import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;
import jakarta.persistence.*;

@Table(name = "roles")
@Entity
public class UserRoleEntity extends BaseEntity{

    @Column
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
