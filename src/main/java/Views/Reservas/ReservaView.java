    package Views.Reservas;

    import Controllers.ControllerReserva;
    import Models.User;
    import com.github.lgooddatepicker.components.DatePicker;
    import com.github.lgooddatepicker.components.TimePicker;

    import javax.swing.*;
    import java.time.LocalDate;
    import java.time.LocalTime;

    public class ReservaView extends JPanel {

        private JPanel PrincipalPanel;
        private JPanel contetPanel;
        private JPanel ReservaPanel;
        private JPanel JPanelReservaAutomatica;
        private JLabel tabbReservaAutomatica;
        private JTextField textFieldReservaAutomatica;
        private JButton buttonReservaAuntomatica;
        private JPanel JPanelReservaManual;
        private JPanel panelActividad;
        private JLabel labelActividad;
        private JTextField textFieldActividad;
        private JPanel jpanelTiempo;
        private JPanel jpanelFecha;
        private JLabel labelFecha;
        private DatePicker datePicker;
        private JPanel jpanelHoraI;
        private JLabel labelHoraI;
        private TimePicker timePickerInicio;
        private JPanel jpanelHoraF;
        private JLabel labelhoraf;
        private TimePicker timePickerFin;
        private JPanel panelRecursos;
        private JPanel panelLabs;
        private JTextField textFieldLabCant;
        private JCheckBox laboratorioCheckBox;
        private JPanel PanelPC;
        private JTextField textFieldCompCant;
        private JCheckBox computadorasCheckBox;
        private JPanel panelProyector;
        private JTextField textFieldProyCant;
        private JCheckBox proyectoresCheckBox;
        private JPanel panelTable;
        private JTable tableReseravas;
        private JScrollBar scrollBar1;
        private JButton buttonAceptar;
        private JButton buttonRechazar;

        private User usuarioLogueado;
        private ControllerReserva controller;

        public ReservaView(User usuarioLogueado) {

            this.usuarioLogueado = usuarioLogueado;
            this.controller = new ControllerReserva();

            add(PrincipalPanel);

            buttonRechazar.addActionListener(e -> {

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
            });

            buttonAceptar.addActionListener(e -> {

                String varActividad = textFieldActividad.getText();

                LocalDate fecha = datePicker.getDate();
                LocalTime horaInicio = timePickerInicio.getTime();
                LocalTime horaFin = timePickerFin.getTime();

                boolean necesitaLab = laboratorioCheckBox.isSelected();
                boolean necesitaPC = computadorasCheckBox.isSelected();
                boolean necesitaProy = proyectoresCheckBox.isSelected();

                int cantidadLab = 0;
                int cantidadPC = 0;
                int cantidadProy = 0;

                if (necesitaLab) {
                    cantidadLab = Integer.parseInt(textFieldLabCant.getText());
                }

                if (necesitaPC) {
                    cantidadPC = Integer.parseInt(textFieldCompCant.getText());
                }

                if (necesitaProy) {
                    cantidadProy = Integer.parseInt(textFieldProyCant.getText());
                }

                controller.crearReserva(
                        usuarioLogueado,
                        varActividad,
                        fecha,
                        horaInicio,
                        horaFin,
                        necesitaLab,
                        cantidadLab,
                        necesitaPC,
                        cantidadPC,
                        necesitaProy,
                        cantidadProy
                );
            });
        }
    }

