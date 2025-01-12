package com.kost.iServices;

import com.kost.models.Kost;
import javafx.collections.ObservableList;

import java.util.List;

public interface IKostServices {
    public void addNewKost(Kost kost);

    public void deleteKost(int kost_id);

    public void updateKost(Kost kost, int kost_id);

    public ObservableList<Kost> showAllKosts();
}
