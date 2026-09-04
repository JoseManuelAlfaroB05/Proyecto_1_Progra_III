package Reserva;

import Login.User;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private DatePicker datePicker;
    private TimePicker timePickerInicio;
    private TimePicker timePickerFin;


    public MainView(User usuarioLogueado) {
        setContentPane(principalPanel);
        setTitle("Sistema de Reserva de Recursos - Usuario logueado: "+usuarioLogueado.getVarId());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldReservaAutomatica.setText("");
                textFieldActividad.setText("");
                textFieldLabCant.setText("");
                textFieldProyCant.setText("");
                textFieldCompCant.setText("");
                datePicker.setDate(null);
                timePickerInicio.setTime(null);
                timePickerFin.setTime(null);
                laboratorioCheckBox.setSelected(false);
                computadorasCheckBox.setSelected(false);
                proyectoresCheckBox.setSelected(false);

            }
        });
    }
}