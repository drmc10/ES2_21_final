import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server implements API {
    private ArrayList<Ebook> bookList;
    private ArrayList<User> database;
    private HashMap<String, String> publisherList;

    public Server() {
        bookList = new ArrayList<>();
        bookList.add(new Epub("The Art Of War", "Filiquarian"));
        bookList.add(new Epub("Quantum Physics for Hippies", "Independent"));
        bookList.add(new Epub("Java For Dummies", "For Dummies"));
        bookList.add(new Epub("Design Patterns: Elements of Reusable Object-Oriented Software", "Addison-Wesley Professional"));
        bookList.add(new Epub("The Pragmatic Programmer", "Addison-Wesley Professional"));

        publisherList = new HashMap<>();
        publisherList.put("Filiquarian", "This is 'Filiquarian' terms of responsability.");
        publisherList.put("Independent", "This is 'Independent' terms of responsability.");
        publisherList.put("For Dummies", "This is 'For Dummies' terms of responsability.");
        publisherList.put("Addison-Wesley Professional", "This is 'Addison-Wesley' Professional terms of responsability.");

        database = new ArrayList<>();
        database.add(new User("user1", "123"));
        database.add(new User("user2", "123"));
        database.add(new User("user3", "123"));
    }

    public User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException {
        for (User user : database) {
            if(user.getUsername().equals(username))
                if(user.getPassword().equals(password)) {
                    return user;
                }
                else
                    throw new IncorrectPasswordException();

            else
                throw new UserDoesntExistException();

        }

        return null;
    }

    public ArrayList<User> getDatabase() {
        return database;
    }

    public ArrayList<Ebook> getBookList() {
        return bookList;
    }

    public void requestBook(int hash, User user) throws BookAlreadyLoanedException {
        Ebook ebook = null;

        for (Ebook ebook1 : bookList) {
            if(ebook1.getHash() == hash) {
                ebook = ebook1;
            }
        }

        getClosestServer();

        for (Loan loan : user.getLoanList()) {
            if(ebook.getHash() == loan.getBookHash())
                throw new BookAlreadyLoanedException();
        }

        showEULA(ebook);

        user.addBookToLoanList(ebook);
    }

    public void getClosestServer() {}

    public void readBook(Ebook ebook) {

    }

    public void showEULA(Ebook ebook) {
        String resTerm = publisherList.get(ebook.getPublisher());
        System.out.println(resTerm);
        System.out.println("\nChoose an option\n1. Accept\n2. Decline");

        boolean exit = false;
        while(!exit) {
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    System.out.println("You have accepted the EULA");
                    exit = true;
                    break;

                case "2":
                    System.out.print("Cannot request book without accepting EULA");
                    break;

                default:
                    System.out.println("Please choose a valid option");
            }
        }
    }
}
