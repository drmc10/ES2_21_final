import java.util.Calendar;
import java.util.Date;

public class Loan {
    private final String bookHash;
    private final Date issuedDate;
    private final Date dateReturned;
    private Date dateToReturn;
    private final boolean paidFine;
    private int numberOfRenewals;
    private final int numberOfDays;

    public Loan(String bookHash, int numberOfDays) throws BookDoesntExistException {
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

    public Loan(String bookHash, Date currentDate, int numberOfDays) throws BookDoesntExistException {
        //If book doesn't exist, an exception is thrown
        BookDatabase.INSTANCE.getBookByHash(bookHash);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -(numberOfDays * 2));

        this.bookHash = bookHash;
        this.issuedDate = calendar.getTime();

        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -(numberOfDays));

        this.dateToReturn = calendar.getTime();
        this.dateReturned = null;
        this.paidFine = false;
        this.numberOfRenewals = 0;
        this.numberOfDays = numberOfDays;
    }

    public double calcFine() { return 0.0d;}

    public void payFine() {}

    public void renewDate() throws RenewLimitExceeded {
        if(numberOfRenewals < 2) {
            numberOfRenewals++;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, this.numberOfDays);
            this.dateToReturn = calendar.getTime();
        } else {
            throw new RenewLimitExceeded();
        }
    }

    public String getBookHash() {
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

    public int getNumberOfRenewals() { return numberOfRenewals; }

    public void setNumberOfRenewals(int numberOfRenewals) {
        if(numberOfRenewals > 2) {
            this.numberOfRenewals = 2;
            return;
        }

        this.numberOfRenewals = numberOfRenewals;
    }
}
