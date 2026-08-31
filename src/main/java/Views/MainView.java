package Views;

import Models.User;

import javax.swing.*;

public class MainView extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JButton button1;
    private JTextField actividadtextField;
    private JButton reservarButton;
    private JButton button2;


    public MainView(User usuarioLogueado) {
        setContentPane(panel1);
        setTitle("Sistema de Reserva de Recursos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
