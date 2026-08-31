package Views;

import Models.User;

import javax.swing.*;

public class MainView extends JFrame{
    private JPanel panel1;


    public MainView(User usuarioLogueado) {
        setContentPane(panel1);
        setTitle("Sistema de Reserva de Recursos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
