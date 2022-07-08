package kz.nis.share.exceptions;

public class UserDetailNotFound extends Exception {
    public UserDetailNotFound() {
    }

    public UserDetailNotFound(String message) {
        super(message);
    }
}
