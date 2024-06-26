package com.burdettracker.budgedtrackerproject.repository;

import com.burdettracker.budgedtrackerproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email);

    User getByEmail(String email);

    Optional<List<User>> findAllByRegisteredOnDate(LocalDate date);

    Optional<List<User>> findAllByEmailContaining(String email);

}
