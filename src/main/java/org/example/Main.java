package org.example;

import Views.Login.LoginView;
import javax.swing.*;
import java.awt.BorderLayout;
import Service.GestorCategorias;

public class Main {
    public static void main(String[] args) {
        LoginView vista = new LoginView();
        vista.setVisible(true);
    }
}