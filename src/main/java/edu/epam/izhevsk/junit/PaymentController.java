package edu.epam.izhevsk.junit;

public class PaymentController {
    private AccountService accountService;
    private DepositService depositService;


    public PaymentController(AccountService accountService, DepositService depositService) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    void deposit(Long amount, Long userId) throws InsufficientFundsException {
       if (accountService.isUserAuthenticated(userId)){
           depositService.deposit(amount, userId);
       }
        else{
           throw new SecurityException("User not authenticated: " + userId);
       }
    }
}
