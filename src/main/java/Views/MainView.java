package Views;

import Models.User;
import Views.Reservas.ReservaView;
import Views.Funcionarios.FuncionariosView;
import Models.Rol;

import Views.Calendarizacion.CalendarizacionView;
import Views.Categoria.CategoriaView;


import javax.swing.*;
import java.awt.BorderLayout;

public class MainView extends JFrame {

    private JPanel principalPanel;
    private JPanel contetPanel;
    private JTabbedPane tabbedPane;
    private JPanel reservaPanel;
    private JPanel calendarizacion;
    private JPanel TabbedPanelUsuariosPanel;
    private JPanel CategoriaView;

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


        // No eliminar las pestañas definidas en el diseñador, para que "Categoria" y "Calendarizacion" sigan visibles.
        if (usuarioLogueado.getVarRol() == Rol.ADMINISTRADOR) {
            tabbedPane.insertTab("Funcionarios", null, new FuncionariosView(), null, 0);
        }

        CalendarizacionView calendarizacionView = new CalendarizacionView();

        calendarizacion.setLayout(new BorderLayout());
        calendarizacion.add(calendarizacionView, BorderLayout.CENTER);

        CategoriaView categoria = new CategoriaView();

        CategoriaView.setLayout(new BorderLayout());
        CategoriaView.add(categoria, BorderLayout.CENTER);

    }
}