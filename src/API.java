import java.util.ArrayList;

public interface API {
    User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException, UserIsNotActiveException, EmptyUsernameException, EmptyPasswordException;
    ArrayList<Ebook> getBookDataBase();
    void requestBook(String hash, String userId) throws BookAlreadyLoanedException, BookDoesntExistException, UserDoesntExistException, RenewLimitExceeded;
    Server getClosestServer(String region) throws ServerNotFoundException;
    void readBook(String userId, Loan loan) throws UserDoesntExistException;
    boolean showEULA(Ebook ebook);
    void blockUser(String userId) throws UserDoesntExistException;
}
