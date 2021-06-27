import java.util.ArrayList;
import java.util.UUID;

public class User {
    private final String username;
    private final String password;
    private final String id;
    private final ArrayList<Loan> loanList;
    private boolean isActive;

    public User(String username, String password) throws MissingPasswordException, EmptyPasswordException,
            MissingUsernameException, EmptyUsernameException, TooFewCharsUsernameException,
            TooManyCharsUsernameException {
        checkConditions(username, password);

        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.isActive = true;
    }

    public User(String username, String password, Boolean isActive) throws MissingUsernameException, MissingPasswordException,
            EmptyUsernameException, EmptyPasswordException, TooFewCharsUsernameException,
            TooManyCharsUsernameException {
        checkConditions(username, password);

        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.isActive = isActive;
    }

    public User(String username, String password, Loan loanToAdd) throws MissingUsernameException, MissingPasswordException, EmptyUsernameException, EmptyPasswordException, MissingLoanException, TooFewCharsUsernameException, TooManyCharsUsernameException {
        checkConditions(username, password);

        if(loanToAdd == null)
            throw new MissingLoanException();

        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.isActive = true;
        this.loanList.add(loanToAdd);
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

    public void addBookToLoanList(Ebook ebook) throws BookDoesntExistException {
        loanList.add(new Loan(ebook.getHash()));
    }

    public ArrayList<Loan> getLoanList() {
        return loanList;
    }

    public boolean isInactive() {
        return !isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private void checkConditions(String username, String password) throws MissingUsernameException, MissingPasswordException, EmptyUsernameException, EmptyPasswordException, TooFewCharsUsernameException, TooManyCharsUsernameException {
        if(username == null)
            throw new MissingUsernameException();
        if(password == null)
            throw new MissingPasswordException();

        if(username.equals(""))
            throw new EmptyUsernameException();
        if(username.length() < 6)
            throw new TooFewCharsUsernameException();
        if(username.length() > 24)
            throw new TooManyCharsUsernameException();

        if(password.equals(""))
            throw new EmptyPasswordException();
        if(password.length() < 6)
            throw new TooFewCharsUsernameException();
        if(password.length() > 24)
            throw new TooManyCharsUsernameException();
    }
}
