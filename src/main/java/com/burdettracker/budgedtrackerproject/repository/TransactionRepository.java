package com.burdettracker.budgedtrackerproject.repository;

import com.burdettracker.budgedtrackerproject.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


}
