import java.util.ArrayList;
import java.util.Date;

public enum UserDatabase {
    INSTANCE;

    private ArrayList<User> userList = new ArrayList<>();

    UserDatabase() {
        try {
            userList.add(new User("testUser1", "testUser1", new Loan(-1751369981, new Date(), 14)));
            userList.add(new User("testUser2", "testUser2"));
            userList.add(new User("testUser3", "testUser3", false));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public User getUserById(String userId) throws UserDoesntExistException {
        for (User user : userList) {
            if(user.getId().equals(userId))
                return user;
        }

        throw new UserDoesntExistException();
    }

    public void reset() {
        userList = new ArrayList<>();
        try {
            userList.add(new User("testUser1", "testUser1", new Loan(-1751369981, new Date(), 14)));
            userList.add(new User("testUser2", "testUser2"));
            userList.add(new User("testUser3", "testUser3", false));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
