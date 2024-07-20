package com.burdettracker.budgedtrackerproject.model.dto.account;

import java.math.BigDecimal;
import java.util.List;

public class AllAccountsInfoDTO {

    private List<AccountDTO> allAccounts;
    private BigDecimal totalBalance;

    public AllAccountsInfoDTO(List<AccountDTO> allAccounts, BigDecimal totalBalance) {
        this.allAccounts = allAccounts;
        this.totalBalance = totalBalance;
    }

    public AllAccountsInfoDTO(List<AccountDTO> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public List<AccountDTO> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(List<AccountDTO> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {

        this.totalBalance = totalBalance;
    }
}
