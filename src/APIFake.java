import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public enum APIFake implements API {
    INSTANCE();

    private final BookDatabase bookDatabase;
    private final UserDatabase userDatabase;

    APIFake() {
        bookDatabase = BookDatabase.INSTANCE;
        userDatabase = UserDatabase.INSTANCE;
    }

    public User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException,
            EmptyUsernameException, EmptyPasswordException, NullParameterException, TooFewCharsException, TooManyCharsException {
        if(username == null || password == null)
            throw new NullParameterException();

        if(username.isEmpty())
            throw new EmptyUsernameException();

        if(password.isEmpty())
            throw new EmptyPasswordException();

        if(username.length() < 5 || password.length() < 5)
            throw new TooFewCharsException();

        if(username.length() > 25 || password.length() > 25)
            throw new TooManyCharsException();

        for (User user : userDatabase.getUserList()) {
            if(user.getUsername().equals(username))
                if(user.getPassword().equals(password)) {
                    return user;
                }
                else
                    throw new IncorrectPasswordException();
        }

        throw new UserDoesntExistException();
    }

    public void requestBook(String hash, String userId) throws BookAlreadyLoanedException, BookDoesntExistException,
            UserDoesntExistException, NullParameterException, InvalidHashException, TooFewCharsException,
            TooManyCharsException, MissingUserIdException {
        if(hash == null || userId == null)
            throw new NullParameterException();

        if(userId.equals(""))
            throw new MissingUserIdException();
        if(hash.equals(""))
            throw new InvalidHashException();

        if(hash.length() != 64)
            throw new InvalidHashException();

        if(userId.length() < 36)
            throw new TooFewCharsException();
        if(userId.length() > 36)
            throw new TooManyCharsException();

        User user = userDatabase.getUserById(userId);

        if(user.isInactive()) {
            System.out.println("Your account is currently blocked, you are unable to request the book");
            return;
        }

        Ebook ebook = bookDatabase.getBookByHash(hash);

        Server server;
        try {
            server = getClosestServer(user.getContinent());
        } catch (ServerNotFoundException | NullParameterException e) {
            server = new Server("Europe");
        }

        for (Loan loan : user.getLoanList()) {
            if(ebook.getHash().equals(loan.getBookHash()) && (loan.getDateToReturn().compareTo(new Date())) > 0)
                throw new BookAlreadyLoanedException();
            else if(ebook.getHash().equals(loan.getBookHash()) && (loan.getDateToReturn().compareTo(new Date())) < 0) {
                try {
                    loan.renewDate();
                    return;
                } catch (RenewLimitExceeded renewLimitExceeded) {
                    if(showEULA(ebook)) {
                        server.requestBook(user.getId(), ebook);
                        return;
                    }
                }
            }
        }

        if(showEULA(ebook))
            server.requestBook(user.getId(), ebook);
    }

    public Server getClosestServer(String continent) throws ServerNotFoundException, NullParameterException {
        if(continent == null)
            throw new NullParameterException();
        return ServerDataBase.INSTANCE.getServer(continent);
    }

    public void readBook(String userId, Loan loan) throws UserDoesntExistException, NullParameterException, TooFewCharsException,
            TooManyCharsException, MissingUserIdException {
        if(userId == null || loan == null)
            throw new NullParameterException();

        if(userId.equals(""))
            throw new MissingUserIdException();

        if(userId.length() < 36)
            throw new TooFewCharsException();
        if(userId.length() > 36)
            throw new TooManyCharsException();

        if(userDatabase.getUserById(userId).isInactive()) {
            System.out.println("Your account is currently blocked, you are unable to read the book");
            return;
        }

        Date currentDate = new Date();
        if(currentDate.compareTo(loan.getDateToReturn()) > 0) {
            System.out.println("The book is no longer available to be read, please renew your loan first");
            return;
        }

        Ebook ebook = null;
        for (Ebook ebook1 : bookDatabase.getBookList()) {
            if(ebook1.getHash().equals(loan.getBookHash())) {
                ebook = ebook1;
                break;
            }
        }
        assert ebook != null;
        try {
            new BookReader(ebook);
        }catch (BookDoesntExistException e) {
            System.out.println("The book chosen doesn't exist");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean showEULA(Ebook ebook) throws NullParameterException {
        if(ebook == null)
            throw new NullParameterException();

        String resTerm = bookDatabase.getPublisherList().get(ebook.getPublisher());

        System.out.println(resTerm);
        System.out.println("\nChoose an option\n1. Accept\n2. Decline");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();

        switch (option) {
            case "1":
                System.out.println("You have accepted the EULA");
                return true;

            case "2":
                System.out.print("Cannot request book without accepting EULA");
                break;

            default:
                System.out.println("Please choose a valid option");
        }

        return false;
    }

    public void blockUser(String userId) throws UserDoesntExistException, NullParameterException, TooFewCharsException,
            TooManyCharsException, MissingUserIdException {
        if(userId == null)
            throw new NullParameterException();
        if(userId.equals(""))
            throw new MissingUserIdException();

        if(userId.length() < 36)
            throw new TooFewCharsException();
        if(userId.length() > 36)
            throw new TooManyCharsException();

        for (User user : userDatabase.getUserList()) {
            if(user.getId().equals(userId)) {
                user.setActive(false);
                return;
            }
        }

        throw new UserDoesntExistException();
    }
}
