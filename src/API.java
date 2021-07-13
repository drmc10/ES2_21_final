import java.util.ArrayList;

public interface API {
    User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException, UserIsNotActiveException, EmptyUsernameException, EmptyPasswordException;
    ArrayList<Ebook> getBookDataBase();
    //Ebook getBookByHash(int hash) throws BookDoesntExistException;
    void requestBook(int hash, String userId) throws BookAlreadyLoanedException, BookDoesntExistException, UserDoesntExistException;
    void getClosestServer();
    void readBook(String userId, Loan loan) throws UserDoesntExistException;
    boolean showEULA(Ebook ebook);
    void blockUser(String userId) throws UserDoesntExistException;
}
