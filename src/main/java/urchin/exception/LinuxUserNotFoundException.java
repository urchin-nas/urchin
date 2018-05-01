package urchin.exception;

import urchin.model.user.Username;

public class LinuxUserNotFoundException extends RuntimeException {

    public LinuxUserNotFoundException(Username username) {
        super("Invalid username " + username);
    }
}
