package com.burdettracker.budgedtrackerproject.init;

import com.burdettracker.budgedtrackerproject.model.entity.UserRoleEntity;
import com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum;
import com.burdettracker.budgedtrackerproject.repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.burdettracker.budgedtrackerproject.model.entity.enums.UserRoleEnum.ADMIN;

@Component
public class RolesRepositoryInit implements CommandLineRunner {

    private final RolesRepository rolesRepository;

    public RolesRepositoryInit(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.rolesRepository.count() == 0) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            UserRoleEntity adminRoleEntity = new UserRoleEntity();

            adminRoleEntity.setRole(ADMIN);
            userRoleEntity.setRole(UserRoleEnum.USER);
            rolesRepository.saveAllAndFlush(List.of(userRoleEntity, adminRoleEntity));
        }
    }

}
