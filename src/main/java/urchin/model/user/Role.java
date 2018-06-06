package urchin.model.user;

public enum Role {

    URCHIN_ADMIN;

    public String getRole() {
        return "ROLE_" + this.name();
    }
}
