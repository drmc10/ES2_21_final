import java.util.Date;

public class Loan {
    private int bookId;
    private Date issuedDate;
    private Date dateReturned;
    private Date dateToReturn;
    private boolean paidFine;

    public Loan(int bookId) {
        this.bookId = bookId;
        this.issuedDate = null;
        this.dateReturned = null;
        this.dateToReturn = null;
        this.paidFine = false;
    }

    public double calcFine() { return 0.0d;}

    public void payFine() {}

    public void renewDate(Date newDate) {
        this.dateToReturn = newDate;
    }

    public int getBookId() {
        return bookId;
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
