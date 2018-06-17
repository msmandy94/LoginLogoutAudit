package services;

import beens.UserCredentials;

/**
 * Created by mandeepsingh on 16/06/18.
 */
public interface UserCredentialsService {
    Boolean validateUserCred(UserCredentials userCredentials);
}
