package kz.nis.share.exceptions;

public class UserNotFound extends Exception {
    public UserNotFound() {
    }

    public UserNotFound(String message) {
        super(message);
    }
}
