package com.burdettracker.budgedtrackerproject.repository;

import com.burdettracker.budgedtrackerproject.model.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    void deleteByAccountId(Long accountId);

    List<Expense> findAllByDateDue(LocalDate date);

    Optional<List<Expense>> findAllByCategory_Category(String category);
}
