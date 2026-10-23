package com.yourdomain;

import com.yourdomain.dao.DatabaseConnection;
import com.yourdomain.ui.PetVaccinationMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initialize();
        new PetVaccinationMenu().start();
    }
}
