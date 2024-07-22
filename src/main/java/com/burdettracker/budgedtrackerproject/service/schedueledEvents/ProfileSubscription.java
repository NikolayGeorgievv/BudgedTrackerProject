package com.burdettracker.budgedtrackerproject.service.schedueledEvents;


import com.burdettracker.budgedtrackerproject.model.entity.Account;
import com.burdettracker.budgedtrackerproject.model.entity.User;
import com.burdettracker.budgedtrackerproject.repository.AccountRepository;
import com.burdettracker.budgedtrackerproject.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Component
public class ProfileSubscription {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public ProfileSubscription(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    //cron = */10.. is for test purposes only
    //        @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(cron = "0 55 23 */1 * *")
    public void payForProfileSubscription() {

        List<User> allUsers = this.userRepository.findAll();

        try {
            allUsers.forEach(user -> {
                int usersRegisteredDay = user.getRegisteredOnDate().getDayOfMonth();
                int todaysDay = LocalDate.now().getDayOfMonth();
                boolean flag = usersRegisteredDay > 29 && LocalDate.now().lengthOfMonth() < usersRegisteredDay;

                if (usersRegisteredDay == todaysDay || flag) {
                    Account account = accountRepository.getByName(user.getAccountNameAssignedForSubscription());
                    if (user.getMembershipType().toString().equals("GOLD")) {
                        BigDecimal newAccAmount = account.getCurrentAmount().subtract(BigDecimal.valueOf(15));
                        account.setCurrentAmount(newAccAmount);
                    } else if (user.getMembershipType().toString().equals("PREMIUM")) {
                        BigDecimal newAccAmount = account.getCurrentAmount().subtract(BigDecimal.valueOf(29));
                        account.setCurrentAmount(newAccAmount);
                    }
                    accountRepository.saveAndFlush(account);
                }
            });
        } catch (NullPointerException e) {
            //TODO: LOG THE EX
        }
    }
}
