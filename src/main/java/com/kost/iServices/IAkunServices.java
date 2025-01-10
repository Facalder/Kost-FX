package com.kost.iServices;

import com.kost.models.Akun;

public interface IAkunServices {
    public void login(Akun akun);

    public void register(Akun akun);

    public void logout();

    public String getLoggedInUsername();

    public String getLoggedInPassword();

    public int getLoggedInUserId();

    public boolean getLoggedInStatus();

    public boolean getRegisteredStatus();
}
