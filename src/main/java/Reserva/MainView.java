package Reserva;

import Login.User;

import javax.swing.*;

public class MainView extends JFrame {
    private JPanel principalPanel;
    private JPanel contetPanel;
    private JTabbedPane tabbedPane;
    private JPanel JPanelReservaAutomatica;
    private JTextField textFieldReservaAutomatica;
    private JButton buttonReservaAuntomatica;
    private JLabel tabbReservaAutomatica;
    private JPanel JPanelReservaManual;
    private JTextField textFieldActividad;
    private JLabel labelActividad;
    private JPanel panelActividad;
    private JLabel labelFecha;
    private JLabel labelHoraI;
    private JLabel labelhoraf;
    private JPanel jpanelTiempo;
    private JPanel jpanelFecha;
    private JPanel jpanelHoraI;
    private JPanel jpanelHoraF;
    private JTextField textFieldLabCant;
    private JTextField textFieldCompCant;
    private JTextField textFieldProyCant;
    private JPanel panelRecursos;
    private JPanel panelLabs;
    private JPanel PanelPC;
    private JPanel panelProyector;
    private JButton buttonAceptar;
    private JButton buttonRechazar;
    private JPanel panelTable;
    private JTable tableReseravas;
    private JCheckBox laboratorioCheckBox;
    private JCheckBox computadorasCheckBox;
    private JCheckBox proyectoresCheckBox;
    private JScrollBar scrollBar1;


    public MainView(User usuarioLogueado) {
        setContentPane(principalPanel);
        setTitle("Sistema de Reserva de Recursos - Usuario logueado: "+usuarioLogueado.getVarId());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}