package me.darthsid.unittesty.utils;

import android.support.annotation.NonNull;

import me.darthsid.unittesty.exceptions.LoginFailedException;

/**
 * Created by darthsid on 11/3/17.
 */

public class LoginModule {
    private NetworkManager nm;

    public LoginModule(NetworkManager m) {
        this.nm = m;
    }

    public String login(String userName, String password) {
        try {
            return getToken(userName, password);
        } catch (Exception e) {
            throw new LoginFailedException();
        }
    }

    @NonNull
    private String getToken(String userName, String password) {
        String returnVal = nm.post("/login", userName + " " + password);
        if (returnVal.equals("false")) {
            throw new LoginFailedException();
        }
        return returnVal;
    }
}
