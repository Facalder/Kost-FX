package com.kost.iServices;

import com.kost.models.Kost;

public interface IKostServices {
    public void addNewKost(Kost kost);

    public void updateKost(Kost kost);

    public void deleteKost(Kost kost);

    public void showAllKosts();
}
