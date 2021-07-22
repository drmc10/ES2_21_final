import java.util.ArrayList;
import java.util.UUID;

public class User {
    private final String username;
    private final String password;
    private final String id;
    private final ArrayList<Loan> loanList;
    private boolean isActive;
    private final String continent;

    public User(String username, String password, String continent) throws MissingPasswordException, EmptyPasswordException,
            MissingUsernameException, EmptyUsernameException, TooFewCharsException,
            TooManyCharsException, MissingContinentException {
        checkConditions(username, password);

        if(continent == null)
            throw new MissingContinentException();
        if(continent.isEmpty())
            throw new MissingContinentException();

        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.isActive = true;
        this.continent = continent;
    }

    public User(String username, String password, Boolean isActive) throws MissingUsernameException, MissingPasswordException,
            EmptyUsernameException, EmptyPasswordException, TooFewCharsException,
            TooManyCharsException {
        checkConditions(username, password);

        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.isActive = isActive;
        this.continent = "Europe";
    }

    public User(String username, String password, Loan loanToAdd) throws MissingUsernameException, MissingPasswordException, EmptyUsernameException, EmptyPasswordException, MissingLoanException, TooFewCharsException, TooManyCharsException {
        checkConditions(username, password);

        if(loanToAdd == null)
            throw new MissingLoanException();

        this.username = username;
        this.password = password;
        this.loanList = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
        this.isActive = true;
        this.loanList.add(loanToAdd);
        this.continent = "Europe";
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

    public void addBookToLoanList(String ebookHash) throws BookDoesntExistException, InvalidHashException, InvalidNumberOfDaysToLoanException {
        loanList.removeIf(loan -> loan.getBookHash().equals(ebookHash));
        loanList.add(new Loan(ebookHash, 14));
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

    private void checkConditions(String username, String password) throws MissingUsernameException, MissingPasswordException,
            EmptyUsernameException, EmptyPasswordException,
            TooFewCharsException, TooManyCharsException {
        if(username == null)
            throw new MissingUsernameException();
        if(password == null)
            throw new MissingPasswordException();

        if(username.equals(""))
            throw new EmptyUsernameException();
        if(username.length() < 5)
            throw new TooFewCharsException();
        if(username.length() > 25)
            throw new TooManyCharsException();

        if(password.equals(""))
            throw new EmptyPasswordException();
        if(password.length() < 5)
            throw new TooFewCharsException();
        if(password.length() > 25)
            throw new TooManyCharsException();
    }

    public String getContinent() { return continent; }
}
