import java.util.ArrayList;

public interface API {
    User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException, UserIsNotActiveException;
    ArrayList<User> getDatabase();
    ArrayList<Ebook> getBookList();
    Ebook getBookByHash(int hash) throws BookDoesntExistException;
    void requestBook(int hash, User user) throws BookAlreadyLoanedException;
    void getClosestServer();
    void readBook(User user, Loan loan);
    boolean showEULA(Ebook ebook);
}
