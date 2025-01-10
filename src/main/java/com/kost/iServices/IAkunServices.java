package com.kost.iServices;

import com.kost.models.Akun;

public interface IAkunServices {
    public void login(Akun akun);

    public void register(Akun akun);

    public String getLoggedInUsername();

    public String getLoggedInPassword();

    public String getLoggedInUserId();
}
