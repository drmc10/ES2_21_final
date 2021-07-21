import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class LoanRenewal {
    @Test
    public void testLoanRenewalOnce() throws InvalidNumberOfDaysToLoanException, BookDoesntExistException,
            InvalidHashException, RenewLimitExceeded {
        Loan loan = new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", new Date(), 14);
        loan.renewDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 14);

        Assertions.assertEquals(calendar.getTime(), loan.getDateToReturn());
    }

    @Test
    public void testLoanRenewalThrice() {
        Assertions.assertThrows(RenewLimitExceeded.class, () -> {
            Loan loan = new Loan("14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", new Date(), 14);
            loan.renewDate();
            loan.renewDate();
            loan.renewDate();
        });
    }
}
