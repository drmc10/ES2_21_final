public interface API {
    User login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException, UserIsNotActiveException, EmptyUsernameException, EmptyPasswordException, NullParameterException, TooFewCharsException, TooManyCharsException;
    void requestBook(String hash, String userId) throws BookAlreadyLoanedException, BookDoesntExistException, UserDoesntExistException, RenewLimitExceeded, InvalidHashException, NullParameterException, TooFewCharsException, TooManyCharsException, MissingUserIdException;
    Server getClosestServer(String continent) throws ServerNotFoundException, NullParameterException;
    void readBook(String userId, Loan loan) throws UserDoesntExistException, NullParameterException, TooFewCharsException, TooManyCharsException, MissingUserIdException;
    boolean showEULA(Ebook ebook) throws NullParameterException;
    void blockUser(String userId) throws UserDoesntExistException, NullParameterException, TooFewCharsException, TooManyCharsException, MissingUserIdException;
}
