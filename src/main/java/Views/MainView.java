package Views;

import Models.User;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;

public class MainView extends JFrame{
    private JPanel principalPanel;
    private JPanel contentPanel;
    private JTabbedPane tabbedPane1;
    private JTextField textFieldReservaAutomatica;
    private JButton buttonReservaAutomatica;
    private JTextField textFieldActividad;
    private JButton button2;
    private JButton button3;
    private JLabel LabelActividad;
    private JPanel JPanelLower;
    private JPanel PanelFecha;
    private JLabel PanelInicio;
    private JPanel PanelFinal;
    private TimePicker HoraFin;
    private TimePicker HoraInicio;
    private DatePicker datePicker;
    private JRadioButton radioButtonLab;
    private JRadioButton radioButton1;
    private JTextField textField1;
    private JRadioButton radioButton2;
    private JTextField textField2;
    private JButton historialDeReservasButton;


    public MainView(User usuarioLogueado) {
        setContentPane(principalPanel);
        setTitle("Sistema de Reserva de Recursos");
        setSize(720, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
