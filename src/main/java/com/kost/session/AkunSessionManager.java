package com.kost.session;

import com.kost.models.Akun;

public class AkunSessionManager {
    private static AkunSessionManager instance;
    private Akun currentAkun;

    private AkunSessionManager() {};

    public static AkunSessionManager getInstance() {
        if (instance == null) {
            instance = new AkunSessionManager();
        }

        return instance;
    }

    public void setCurrentAkunRole(String role) {
        this.currentAkun.setRole(role);
    }

    public String getCurrentAkunRole() {
        return currentAkun.getRole();
    }

    public void setCurrentAkun(Akun akun) {
        currentAkun = akun;
    }

    public Akun getCurrentAkun() {
        return currentAkun;
    }

    public void removeCurrentAkun() {
        this.currentAkun = null;
    }
}
