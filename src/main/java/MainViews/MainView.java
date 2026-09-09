package MainViews;

import Recursos.User;
import Calendarizacion.CalendarizacionView;
import Funcionarios.FuncionariosView;
import Reserva.ReservaView;
import Categoria.CategoriaView;
import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private JPanel principalPanel;
    private JPanel contetPanel;
    private JTabbedPane tabbedPane;

    private JPanel reservaPanel;
    private JPanel calendarizacion;
    private JPanel CategoriaView;
    private JPanel funcionariosPanel;

    private User usuarioLogueado;

    public MainView(User usuarioLogueado) {

        this.usuarioLogueado = usuarioLogueado;

        setTitle("Sistema de Reserva de Recursos - Usuario logueado: "
                + usuarioLogueado.getVarId());

        setContentPane(principalPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1000, 700);
        setLocationRelativeTo(null);

        cargarVistas();
    }

    private void cargarVistas() {
        ReservaView reservaView =
                new ReservaView(usuarioLogueado);

        reservaPanel.setLayout(new BorderLayout());
        reservaPanel.add(
                reservaView,
                BorderLayout.CENTER
        );

        CalendarizacionView calendarizacionView =
                new CalendarizacionView();

        calendarizacion.setLayout(new BorderLayout());
        calendarizacion.add(
                calendarizacionView,
                BorderLayout.CENTER
        );

        CategoriaView categoriaView =
                new CategoriaView();

        CategoriaView.setLayout(new BorderLayout());
        CategoriaView.add(
                categoriaView,
                BorderLayout.CENTER
        );

        FuncionariosView funcionariosView =
                new FuncionariosView();

        funcionariosPanel.setLayout(new BorderLayout());
        funcionariosPanel.add(
                funcionariosView,
                BorderLayout.CENTER
        );
    }
}