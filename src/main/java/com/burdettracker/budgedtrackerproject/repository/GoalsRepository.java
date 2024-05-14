package com.burdettracker.budgedtrackerproject.repository;

import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalsRepository extends JpaRepository<Goal, Long> {

    List<Goal> getAllByUser_Email(String email);

}
