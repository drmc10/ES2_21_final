import java.util.HashMap;

public class LoginDB {
    private static LoginDB instance;
    private HashMap<String, String> database;

    private LoginDB() {
        database = new HashMap<>();
    }

    public static LoginDB getInstance() {
        if(instance == null)
            instance = new LoginDB();

        return instance;
    }

    public void login(String username, String password) throws UserDoesntExistException, IncorrectPasswordException {
        if(!database.containsKey(username))
            throw new UserDoesntExistException();

        if(!database.get(username).equals(password))
            throw new IncorrectPasswordException();
    }

    public HashMap<String, String> getDatabase() {
        return database;
    }
}
