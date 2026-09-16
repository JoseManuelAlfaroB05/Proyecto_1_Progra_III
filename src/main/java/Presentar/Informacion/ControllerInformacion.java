package Presentar.Informacion;

import Presentar.Login.LoginView.LoginView;

import javax.swing.*;

public class ControllerInformacion {

    public void cerrarSesion(JFrame ventanaActual) {
        ventanaActual.dispose();

        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }
}