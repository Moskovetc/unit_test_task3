package edu.epam.izhevsk.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    private long userId;
    private long amount;

    @InjectMocks
    PaymentController paymentController;
    @Mock
    DepositService depositService;
    @Mock
    AccountService accountService;

    @Before
    public void setUp() throws InsufficientFundsException {
        userId = 100L;
        amount = 100L;
        when(accountService.isUserAuthenticated(userId)).thenReturn(true);
        when(depositService.deposit(lt(amount), anyLong())).thenReturn("successful");
        when(depositService.deposit(gt(amount), anyLong())).thenThrow(InsufficientFundsException.class);
    }

    @Test(expected = InsufficientFundsException.class)
    public void insufficientFundsExceptionTest() throws InsufficientFundsException {
        throw new InsufficientFundsException();
    }

    @Test
    public void paymentControllerSuccessfulDepositTest() throws InsufficientFundsException {
        paymentController.deposit(50L, 100L);
        verify(accountService, times(1)).isUserAuthenticated(eq(userId));
    }

    @Test(expected = SecurityException.class)
    public void paymentControllerFailedDepositOfLargeAmountTest() throws InsufficientFundsException {
        paymentController.deposit(50L, 101L);
    }

    @Test(expected = InsufficientFundsException.class)
    public void paymentControllerLargeAmountTest() throws InsufficientFundsException {
        paymentController.deposit(150L, 100L);
    }
}
