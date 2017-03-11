package me.darthsid.unittesty.exceptions;

/**
 * Created by darthsid on 11/3/17.
 */

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Login failed");
    }
}
