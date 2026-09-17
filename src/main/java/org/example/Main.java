package org.example;

import Presentar.Login.LoginView.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
        }

        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }
}