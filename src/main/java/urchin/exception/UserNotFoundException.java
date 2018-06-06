package urchin.exception;

import urchin.model.user.UserId;
import urchin.model.user.Username;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UserId userId) {
        super("Invalid userId " + userId);
    }

    public UserNotFoundException(Username username) {
        super("Invalid username " + username);
    }
}
