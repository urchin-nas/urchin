package urchin.exception;

import urchin.model.user.AdminId;
import urchin.model.user.Username;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Username username) {
        super("Invalid username " + username);
    }

    public AdminNotFoundException(AdminId adminId) {
        super("Invalid adminId " + adminId);
    }
}
