import java.util.ArrayList;
import java.util.UUID;

public class User {
    private String username;
    private String password;
    private String id;
    private ArrayList<Loan> loanList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<Loan>();
        this.id = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void addBookToLoanList(Ebook ebook) {
        loanList.add(new Loan(ebook.getHash()));
    }

    public ArrayList<Loan> getLoanList() {
        return loanList;
    }
}
