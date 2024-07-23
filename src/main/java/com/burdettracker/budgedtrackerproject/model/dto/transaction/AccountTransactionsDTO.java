package com.burdettracker.budgedtrackerproject.model.dto.transaction;

import java.util.List;

public class AccountTransactionsDTO {

    private List<TransactionInfoDTO> allTransactions;


    public AccountTransactionsDTO() {
    }

    public AccountTransactionsDTO(List<TransactionInfoDTO> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public List<TransactionInfoDTO> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(List<TransactionInfoDTO> allTransactions) {
        this.allTransactions = allTransactions;
    }
}
