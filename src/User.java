import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int id;
    private ArrayList<Loan> loanList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<Loan>();

        LoginDB instance = LoginDB.getInstance();
        this.id = instance.getDatabase().size() + 1;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Loan> getLoanList() {
        return loanList;
    }
}
