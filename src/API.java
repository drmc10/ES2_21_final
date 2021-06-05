import java.util.ArrayList;

public interface API {
    User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException;
    ArrayList<User> getDatabase();
    ArrayList<Ebook> getBookList();
    void requestBook(int hash, User user) throws BookAlreadyLoanedException;
    void getClosestServer();
    void readBook(Ebook ebook);
    void showEULA(Ebook ebook);
}
