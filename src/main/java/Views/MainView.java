package Views;

import Models.User;
import Views.Usuarios.UsuarioView;
import Views.Reservas.ReservaView;
import Views.Calendarizacion.CalendarizacionView;

import javax.swing.*;
import java.awt.BorderLayout;

public class MainView extends JFrame {

    private JPanel principalPanel;
    private JPanel contetPanel;
    private JTabbedPane TabbedPanel;
    private JPanel reservaPanel;
    private JPanel calendarizacion;
    private JPanel TabbedPanelUsuariosPanel;
    private JPanel usuarioView;

    private User usuarioLogueado;

    public MainView(User usuarioLogueado) {

        this.usuarioLogueado = usuarioLogueado;

        setContentPane(principalPanel);
        setTitle("Sistema de Reserva de Recursos - Usuario logueado: "
                + usuarioLogueado.getVarId());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ReservaView reservaView = new ReservaView(usuarioLogueado);

        reservaPanel.setLayout(new BorderLayout());
        reservaPanel.add(reservaView, BorderLayout.CENTER);

        //tab de calendarizacion
        CalendarizacionView calendarizacionView = new CalendarizacionView();

        calendarizacion.setLayout(new BorderLayout());
        calendarizacion.add(calendarizacionView, BorderLayout.CENTER);

        //tab de usuarios
        UsuarioView vistaUsuarios = new UsuarioView();

        usuarioView.setLayout(new BorderLayout());
        usuarioView.add(vistaUsuarios, BorderLayout.CENTER);
    }
}