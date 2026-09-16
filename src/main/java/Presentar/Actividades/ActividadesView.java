package Presentar.Actividades;

import Recursos.Reserva;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class ActividadesView extends JPanel implements PropertyChangeListener {
    private DatePicker fechaReferencia = new DatePicker();
    private JTable tabla = new JTable();
    private final ControllerActividades controller = new ControllerActividades();
    private final ModelActividades model = controller.getModel();

    public ActividadesView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        fechaReferencia.setDate(LocalDate.now());
        model.addPropertyChangeListener(this);
        add(crearFiltro(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        controller.cargarSemana(fechaReferencia.getDate());
    }

    private JPanel crearFiltro() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Semana"));
        panel.add(new JLabel("Fecha de referencia"));
        panel.add(fechaReferencia);

        JButton cargar = new JButton("Cargar");
        cargar.addActionListener(e -> cargarSemana());
        panel.add(cargar);

        JButton pdf = new JButton("PDF");
        pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.generarPDF();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ActividadesView.this, "No fue posible generar el PDF.", "Actividades", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(pdf);

        return panel;
    }

    private JScrollPane crearTabla() {
        tabla.setRowHeight(54);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setGridColor(new Color(180, 180, 180));
        return new JScrollPane(tabla);
    }

    private void cargarSemana() {
        controller.cargarSemana(fechaReferencia.getDate());
    }

    private void actualizarTabla() {
        tabla.setModel(new TableModelActividades(model));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);

        for (int i = 1; i < 8; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(145);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ModelActividades.SEMANA.equals(evt.getPropertyName())) {
            actualizarTabla();
        }
    }
}