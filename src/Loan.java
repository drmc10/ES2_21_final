import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Loan {
    private final int bookHash;
    private final Date issuedDate;
    private final Date dateReturned;
    private Date dateToReturn;
    private final boolean paidFine;
    private int numberOfRenewals;
    private int numberOfDays;

    public Loan(int bookHash, int numberOfDays) throws BookDoesntExistException {
        //If book doesn't exist, an exception is thrown
        BookDatabase.INSTANCE.getBookByHash(bookHash);

        Calendar calendar = Calendar.getInstance();

        this.bookHash = bookHash;
        this.issuedDate = new Date();

        calendar.setTime(issuedDate);
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);

        this.dateToReturn = calendar.getTime();
        this.dateReturned = null;
        this.paidFine = false;
        this.numberOfRenewals = 0;
        this.numberOfDays = numberOfDays;
    }

    public Loan(int bookHash, Date currentDate, int numberofDays) throws BookDoesntExistException {
        //If book doesn't exist, an exception is thrown
        BookDatabase.INSTANCE.getBookByHash(bookHash);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -(numberofDays * 2));

        this.bookHash = bookHash;
        this.issuedDate = calendar.getTime();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -(numberofDays));

        this.dateToReturn = calendar.getTime();
        this.dateReturned = null;
        this.paidFine = false;
        this.numberOfRenewals = 0;
        this.numberOfDays = numberofDays;
    }

    public double calcFine() { return 0.0d;}

    public void payFine() {}

    public void renewDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateToReturn);
        calendar.add(Calendar.DAY_OF_MONTH, this.numberOfDays);
        this.dateToReturn = calendar.getTime();
    }

    public int getBookHash() {
        return bookHash;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public Date getDateToReturn() {
        return dateToReturn;
    }

    public boolean isPaidFine() {
        return paidFine;
    }
}
