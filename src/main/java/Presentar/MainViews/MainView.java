package Presentar.MainViews;

import Recursos.User;
import Presentar.Calendarizacion.CalendarizacionView;
import Presentar.Funcionarios.FuncionariosView;
import Presentar.Reserva.ReservaView;
import Presentar.Categoria.CategoriaView;
import Presentar.Recursos.RecursosView;
import Presentar.Actividades.ActividadesView;
import Presentar.Estadisticas.EstadisticasView;
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
    private JPanel recursosPanel;
    private JPanel actividadesPanel;
    private JPanel estadisticasPanel;

    private User usuarioLogueado;
    private ReservaView reservaView;

    public MainView(User usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;

        setTitle("Sistema de Presentar.Reserva de Recursos - Usuario logueado: "
                + usuarioLogueado.getVarId());

        setContentPane(principalPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1920,1080);
        setLocationRelativeTo(null);

        cargarVistas();
    }

    private void cargarVistas() {
        reservaView = new ReservaView(usuarioLogueado);

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

        RecursosView recursosView = new RecursosView();

        recursosPanel.setLayout(new BorderLayout());
        recursosPanel.add(
                recursosView,
                BorderLayout.CENTER
        );

        ActividadesView actividadesView = new ActividadesView();

        actividadesPanel.setLayout(new BorderLayout());
        actividadesPanel.add(
                actividadesView,
                BorderLayout.CENTER
        );

        EstadisticasView estadisticasView = new EstadisticasView();

        estadisticasPanel.setLayout(new BorderLayout());
        estadisticasPanel.add(
                estadisticasView,
                BorderLayout.CENTER
        );
    }

    public void recargarReservas() {
        reservaView.recargarTabla();
    }
}