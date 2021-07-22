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
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan(
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                -1));
    }

    @Test
    public void testLoanCons1NullNumberOfDays() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan(
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
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
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan(
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                new Date(), -1));
    }

    @Test
    public void testLoanCons2NullNumberOfDays() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () -> new Loan(
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                new Date(), 0));
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

    @Test
    public void testLoanConst2NullDate() {
        Assertions.assertThrows(NullParameterException.class, () -> new Loan(
                "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                null, 14));
    }

    @Test
    public void testLoanCons1InferiorNumberOfDays() {
        Assertions.assertDoesNotThrow(() -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                1));
    }

    @Test
    public void testLoanCons1SuperiorNumberOfDays() {
        Assertions.assertDoesNotThrow(() -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                14));
    }

    @Test
    public void testLoanCons2InferiorNumberOfDays() {
        Assertions.assertDoesNotThrow(() -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                new Date(),
                1));
    }

    @Test
    public void testLoanCons2SuperiorNumberOfDays() {
        Assertions.assertDoesNotThrow(() -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                new Date(),
                14));
    }

    @Test
    public void testLoanCons2InferiorDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, Calendar.JANUARY, 1);
        Date date = calendar.getTime();

        Assertions.assertDoesNotThrow(() -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                date, 14));
    }

    @Test
    public void testLoanCons2SuperiorDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2099, Calendar.DECEMBER, 31);
        Date date = calendar.getTime();

        Assertions.assertDoesNotThrow(() -> new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364",
                date, 14));
    }

    @Test
    public void testNumberOfDaysAbove() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () ->
                new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", new Date(), 29));
    }

    @Test
    public void testNumberOfDaysBellow() {
        Assertions.assertThrows(InvalidNumberOfDaysToLoanException.class, () ->
                new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", new Date(), 0));
    }

    @Test
    public void testDateBellow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1969, Calendar.DECEMBER, 31);
        Date date = calendar.getTime();

        Assertions.assertThrows(InvalidDateException.class, () ->
                new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", date, 14));
    }

    @Test
    public void testDateAbove() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2100, Calendar.JANUARY, 1);
        Date date = calendar.getTime();

        Assertions.assertThrows(InvalidDateException.class, () ->
                new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", date, 14));
    }

    @Test
    public void testHashAbove64() {
        Assertions.assertThrows(InvalidHashException.class, () ->
                new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D0845873641", 14));
    }

    @Test
    public void testHashBellow64() {
        Assertions.assertThrows(InvalidHashException.class, () ->
                new Loan("1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D08458736", 14));
    }
}
