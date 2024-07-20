package com.burdettracker.budgedtrackerproject.service.account;

import com.burdettracker.budgedtrackerproject.model.dto.account.AccountDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.AllAccountsInfoDTO;
import com.burdettracker.budgedtrackerproject.model.dto.account.EditAccountInfoDTO;
import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import com.burdettracker.budgedtrackerproject.service.expense.ExpenseService;
import com.burdettracker.budgedtrackerproject.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAccountsReturnsCorrectData() {
        String email = "test@test.com";
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        when(accountRepository.getAllByUser_Email(email)).thenReturn(accounts);
        AccountDTO accountDTO1 = new AccountDTO();
        accountDTO1.setCurrentAmount(BigDecimal.valueOf(100));
        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setCurrentAmount(BigDecimal.valueOf(200));
        when(modelMapper.map(any(), any())).thenReturn(new AccountDTO[]{accountDTO1, accountDTO2});

        AllAccountsInfoDTO allAccountsInfoDTO = accountService.getAllAccounts(email);

        assertEquals(2, allAccountsInfoDTO.getAllAccounts().size());
        assertEquals(BigDecimal.valueOf(300.0), allAccountsInfoDTO.getTotalBalance());
        verify(accountRepository).getAllByUser_Email(email);
        verify(modelMapper).map(accounts, AccountDTO[].class);
    }

    @Test
    void updateAccountByIdUpdatesAccountName() {
        EditAccountInfoDTO editAccountInfoDTO = new EditAccountInfoDTO();
        editAccountInfoDTO.setId("1");
        editAccountInfoDTO.setNewAccountName("New Name");
        Account account = new Account();
        account.setName("Old Name");
        when(accountRepository.findFirstById(anyLong())).thenReturn(account);

        accountService.updateAccountById(editAccountInfoDTO, "test@test.com");

        assertEquals("New Name", account.getName());
        verify(accountRepository).saveAndFlush(account);
    }

    @Test
    void deleteAccountByIdDeletesAccount() {
        String accountId = "1";

        accountService.deleteAccountById(accountId);

        verify(expenseService).deleteByAccountId(Long.parseLong(accountId));
        verify(accountRepository).deleteById(Long.parseLong(accountId));
    }

    @Test
    void getByNameReturnsCorrectAccount() {
        String name = "Test Account";
        Account account = new Account();
        account.setName(name);
        when(accountRepository.getByName(name)).thenReturn(account);

        Account result = accountService.getByName(name);

        assertEquals(name, result.getName());
    }

    @Test
    void getAccountDTOByIdReturnsCorrectAccount() {
        String accountId = "1";
        Account account = new Account();
        account.setId(1L);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        when(accountRepository.getReferenceById(anyLong())).thenReturn(account);
        when(modelMapper.map(any(), any())).thenReturn(accountDTO);

        AccountDTO result = accountService.getAccountDTOById(accountId);

        assertEquals(account.getId(), result.getId());
        verify(accountRepository).getReferenceById(Long.parseLong(accountId));
        verify(modelMapper).map(account, AccountDTO.class);
    }

    @Test
    void saveAllAndFlushSavesAllAccounts() {
        List<Account> accounts = Arrays.asList(new Account(), new Account());

        accountService.saveAllAndFlush(accounts);

        verify(accountRepository).saveAllAndFlush(accounts);
    }

    @Test
    void saveAndFlushSavesAccount() {
        Account account = new Account();

        accountService.saveAndFlush(account);

        verify(accountRepository).saveAndFlush(account);
    }

    @Test
    void getByIdReturnsCorrectAccount() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        when(accountRepository.getReferenceById(id)).thenReturn(account);

        Account result = accountService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void getTotalBalanceReturnsCorrectBalance() {
        List<AccountDTO> accounts = Arrays.asList(new AccountDTO(), new AccountDTO());
        accounts.get(0).setCurrentAmount(BigDecimal.valueOf(100));
        accounts.get(1).setCurrentAmount(BigDecimal.valueOf(200));

        double result = accountService.getTotalBalance(accounts);

        assertEquals(300, result);
    }
}