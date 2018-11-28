package edu.epam.izhevsk.junit;


public interface DepositService {
    String deposit(Long amount, Long userId) throws InsufficientFundsException;
}
