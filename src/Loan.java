import java.util.Calendar;
import java.util.Date;

public class Loan {
    private final int bookHash;
    private final Date issuedDate;
    private final Date dateReturned;
    private Date dateToReturn;
    private final boolean paidFine;

    public Loan(int bookHash) throws BookDoesntExistException {
        //If book doesn't exist, an exception is thrown
        Server.INSTANCE.getBookByHash(bookHash);

        Calendar calendar = Calendar.getInstance();

        this.bookHash = bookHash;
        this.issuedDate = new Date();

        calendar.setTime(issuedDate);
        calendar.add(Calendar.DAY_OF_MONTH, 14);

        this.dateToReturn = calendar.getTime();
        this.dateReturned = null;
        this.paidFine = false;
    }

    public Loan(int bookHash, Date currentDate) throws BookDoesntExistException {
        //If book doesn't exist, an exception is thrown
        Server.INSTANCE.getBookByHash(bookHash);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -28);

        this.bookHash = bookHash;
        this.issuedDate = calendar.getTime();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -14);

        this.dateToReturn = calendar.getTime();
        this.dateReturned = null;
        this.paidFine = false;
    }

    public double calcFine() { return 0.0d;}

    public void payFine() {}

    public void renewDate(Date newDate) {
        this.dateToReturn = newDate;
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
