import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class LoanConstructor {
    @Test
    public void testLoanConstEmptyHash() {
        Assertions.assertThrows(InvalidHashException.class, () -> new Loan("", 14));
    }

    @Test
    public void testLoanCons1NegativeNumberOfDays() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                -1));
    }

    @Test
    public void testLoanCons1NullNumberOfDays() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                0));
    }

    @Test
    public void testLoanCons1ok() {
        Assertions.assertDoesNotThrow(() -> {
            Loan loan = new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                    14);
            Assertions.assertEquals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                    loan.getBookHash());
        });
    }

    @Test
    public void testLoanCons2NegativeNumberOfDays() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                new Date(), -1));
    }

    @Test
    public void testLoanCons2NullNumberOfDays() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                new Date(), 0));
    }

    @Test
    public void testLoanConstWrongHash2() {
        Assertions.assertThrows(InvalidHashException.class, () -> new Loan("123123",new Date(), 14));
    }

    @Test
    public void testLoanCons2Ok() {
        Assertions.assertDoesNotThrow(() -> {
            Date currentDate = new Date();
            int numberOfDays = 14;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -(numberOfDays * 2));
            Date issuedDate = calendar.getTime();

            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, -numberOfDays);
            Date dateToReturn = calendar.getTime();

            Loan loan = new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                    currentDate, numberOfDays);
            Assertions.assertEquals("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                    loan.getBookHash());
            Assertions.assertEquals(issuedDate.getTime(), loan.getIssuedDate().getTime());
            Assertions.assertEquals(dateToReturn, loan.getDateToReturn());
        });
    }
}
