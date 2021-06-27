import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public enum Server implements API {
    INSTANCE;

    private final ArrayList<Ebook> bookList = new ArrayList<>();
    private final ArrayList<User> database = new ArrayList<>();
    private final HashMap<String, String> publisherList = new HashMap<>();

    Server() {
        try {
            bookList.add(new Epub("The Art Of War", "Filiquarian", 110));
            bookList.add(new Pdf("Quantum Physics for Hippies", "Independent", 175));
            bookList.add(new Epub("Java For Dummies", "For Dummies", 504));
            bookList.add(new Pdf("Design Patterns: Elements of Reusable Object-Oriented Software", "Addison-Wesley Professional", 416));
            bookList.add(new Epub("The Pragmatic Programmer", "Addison-Wesley Professional", 352));
            bookList.add(new Pdf("Blockchain For Dummies", "For Dummies", 256));

            publisherList.put("Filiquarian", "This is 'Filiquarian' terms of responsability.");
            publisherList.put("Independent", "This is 'Independent' terms of responsability.");
            publisherList.put("For Dummies", "This is 'For Dummies' terms of responsability.");
            publisherList.put("Addison-Wesley Professional", "This is 'Addison-Wesley' Professional terms of responsability.");

            database.add(new User("user1", "123", new Loan(-1751369981, new Date())));
            database.add(new User("user2", "123"));
            database.add(new User("user3", "123", false));
        }catch (Exception ignored) {}
    }

    public User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException {
        for (User user : database) {
            if(user.getUsername().equals(username))
                if(user.getPassword().equals(password)) {
                    //Either use this condition or use it in the requestBook method and readBook method
                    /*if(user.isInactive())
                        throw new UserIsNotActiveException();*/

                    return user;
                }
                else
                    throw new IncorrectPasswordException();
        }

        throw new UserDoesntExistException();
    }

    public ArrayList<User> getDatabase() { return database; }

    public ArrayList<Ebook> getBookList() { return bookList; }

    public Ebook getBookByHash(int hash) throws BookDoesntExistException {
        for (Ebook ebook : bookList) {
            if(ebook.getHash() == hash) {
                return ebook;
            }
        }

        throw new BookDoesntExistException();
    }

    public void requestBook(int hash, User user) throws BookAlreadyLoanedException, BookDoesntExistException {
        //Either use this condition in addition to readBook method or use it in the login method
        if(user.isInactive()) {
            System.out.println("Your account is currently blocked, you are unable to request the book");
            return;
        }

        Ebook ebook = null;

        for (Ebook ebook1 : bookList) {
            if(ebook1.getHash() == hash) {
                ebook = ebook1;
            }
        }

        getClosestServer();

        assert ebook != null;
        for (Loan loan : user.getLoanList()) {
            if(ebook.getHash() == loan.getBookHash())
                throw new BookAlreadyLoanedException();
        }

        if(showEULA(ebook))
            user.addBookToLoanList(ebook);

    }

    public void getClosestServer() {}

    public void readBook(User user, Loan loan) {
        //Either use this condition in addition to requestBook method or use it in the login method
        if(user.isInactive()) {
            System.out.println("Your account is currently blocked, you are unable to read the book");
            return;
        }

        Date currentDate = new Date();
        if(currentDate.compareTo(loan.getDateToReturn()) > 0) {
            System.out.println("The book is no longer available to be read, please renew your loan first");
            return;
        }

        Ebook ebook = null;
        for (Ebook ebook1 : bookList) {
            if(ebook1.getHash() == loan.getBookHash()) {
                ebook = ebook1;
                break;
            }
        }
        assert ebook != null;
        try {
            new BookReader(ebook);
        }catch (BookDoesntExistException e) {
            System.out.println("The book chosen doesn't exist");
        }

    }

    public boolean showEULA(Ebook ebook) {
        String resTerm = publisherList.get(ebook.getPublisher());

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

    public void blockUser(User user) { user.setActive(false); }
}
