public class Server {
    private final String region;

    public Server(String region) {
        this.region = region;
    }

    public void requestBook(String userId, Ebook ebook) throws UserDoesntExistException, BookDoesntExistException {
        UserDatabase.INSTANCE.getUserById(userId).addBookToLoanList(ebook.getHash());
    }

    public String getRegion() {
        return region;
    }
}