package org.example;

import Controllers.ControllerLogin;
import Models.User;

import Views.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView vista = new LoginView();
        vista.setVisible(true);
    }
}