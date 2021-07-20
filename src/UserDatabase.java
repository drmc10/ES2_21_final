import java.util.ArrayList;
import java.util.Date;

public enum UserDatabase {
    INSTANCE();

    private ArrayList<User> userList;

    UserDatabase() {
        userList = new ArrayList<>();
        try {
            userList.add(new User("testUser1", "testUser1", new Loan(
                    "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", new Date(), 14)));
            userList.add(new User("testUser2", "testUser2", "America"));
            userList.add(new User("testUser3", "testUser3", false));
            userList.add(new User("testUser4", "testUser4", new Loan(
                    "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", new Date(), 14)));

            userList.get(3).getLoanList().get(0).setNumberOfRenewals(2);
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
            userList.add(new User("testUser1", "testUser1", new Loan(
                    "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", new Date(), 14)));
            userList.add(new User("testUser2", "testUser2", "America"));
            userList.add(new User("testUser3", "testUser3", false));
            userList.add(new User("testUser4", "testUser4", new Loan(
                    "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", new Date(), 14)));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
