package Presentar.MainViews;

import Recursos.Rol;
import Recursos.User;
import Presentar.Calendarizacion.CalendarizacionView;
import Presentar.Funcionarios.FuncionariosView;
import Presentar.Reserva.ReservaView;
import Presentar.Categoria.CategoriaView;
import Presentar.Recursos.RecursosView;
import Presentar.Actividades.ActividadesView;
import Presentar.Estadisticas.EstadisticasView;
import Presentar.Informacion.InformacionView;

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
    private JPanel InformacionPanel;

    private User usuarioLogueado;
    private ReservaView reservaView;
    private CalendarizacionView calendarizacionView;
    private RecursosView recursosView;
    private InformacionView informacionView;

    public MainView(User usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
        setTitle("Sistema de Reserva de Recursos - Usuario logueado: " + usuarioLogueado.getVarId());
        setContentPane(principalPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        cargarVistas();
        configurarPestanas();

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() == reservaPanel) {
                    reservaView.recargarDatos();
                }
                if (tabbedPane.getSelectedComponent() == calendarizacion) {
                    calendarizacionView.recargarDatos();
                }
                if (tabbedPane.getSelectedComponent() == recursosPanel) {
                    recursosView.recargarDatos();
                }
            }
        });
    }

    private void cargarVistas() {
        reservaView = new ReservaView(usuarioLogueado);
        reservaPanel.setLayout(new BorderLayout());
        reservaPanel.add(reservaView, BorderLayout.CENTER);

        calendarizacionView = new CalendarizacionView();
        calendarizacion.setLayout(new BorderLayout());
        calendarizacion.add(calendarizacionView, BorderLayout.CENTER);

        CategoriaView categoriaView = new CategoriaView();
        CategoriaView.setLayout(new BorderLayout());
        CategoriaView.add(categoriaView, BorderLayout.CENTER);

        FuncionariosView funcionariosView = new FuncionariosView();
        funcionariosPanel.setLayout(new BorderLayout());
        funcionariosPanel.add(funcionariosView, BorderLayout.CENTER);

        recursosView = new RecursosView();
        recursosPanel.setLayout(new BorderLayout());
        recursosPanel.add(recursosView, BorderLayout.CENTER);

        ActividadesView actividadesView = new ActividadesView();
        actividadesPanel.setLayout(new BorderLayout());
        actividadesPanel.add(actividadesView, BorderLayout.CENTER);

        EstadisticasView estadisticasView = new EstadisticasView();
        estadisticasPanel.setLayout(new BorderLayout());
        estadisticasPanel.add(estadisticasView, BorderLayout.CENTER);

        informacionView = new InformacionView(usuarioLogueado);
        InformacionPanel.setLayout(new BorderLayout());
        InformacionPanel.add(informacionView, BorderLayout.CENTER);
    }

    private void configurarPestanas() {
        Rol rol = usuarioLogueado.getVarRol();

        if (rol == Rol.FUNCIONARIO) {
            tabbedPane.remove(CategoriaView);
            tabbedPane.remove(funcionariosPanel);
            tabbedPane.remove(recursosPanel);
        }

        if (rol == Rol.ADMINISTRADOR) {
            tabbedPane.remove(reservaPanel);
        }
    }

    public void recargarReservas() {
        reservaView.recargarTabla();
    }
}