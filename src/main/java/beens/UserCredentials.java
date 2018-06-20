package beens;

/**
 * Created by mandeepsingh on 16/06/18.
 */

public class UserCredentials {
    String userId;
    String password;

    public UserCredentials(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
