public class Server {
    private final String region;

    public Server(String region) {
        this.region = region;
    }

    public void requestBook(String userId, Ebook ebook) throws UserDoesntExistException, BookDoesntExistException {
        try {
            UserDatabase.INSTANCE.getUserById(userId).addBookToLoanList(ebook.getHash());
        } catch (InvalidHashException | InvalidNumberOfDaysToLoanException e) {
            System.out.println("The Book selected is invalid, please try again");
        }
    }

    public String getRegion() {
        return region;
    }
}