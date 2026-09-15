package Presentar.Estadisticas;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstadisticasView extends JPanel implements PropertyChangeListener {
    private final ControllerEstadisticas controller = new ControllerEstadisticas();
    private final ModelEstadisticas model = controller.getModel();
    private final DatePicker recursosDesde = new DatePicker();
    private final DatePicker recursosHasta = new DatePicker();
    private final DatePicker actividadesDesde = new DatePicker();
    private final DatePicker actividadesHasta = new DatePicker();
    private final JTable tablaRecursos = new JTable();
    private final JTable tablaActividades = new JTable();
    private final GraficoBarras graficoRecursos = new GraficoBarras(Color.BLUE);
    private final GraficoBarras graficoActividades = new GraficoBarras(Color.RED);

    public EstadisticasView() {
        setLayout(new GridLayout(1, 2, 14, 0));
        setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        LocalDate hoy = LocalDate.now();
        recursosDesde.setDate(hoy.minusDays(30));
        recursosHasta.setDate(hoy);
        actividadesDesde.setDate(hoy.minusDays(30));
        actividadesHasta.setDate(hoy);
        model.addPropertyChangeListener(this);
        add(crearPanelRecursos());
        add(crearPanelActividades());
        cargarTodo();
    }

    private JPanel crearPanelRecursos() {
        JPanel panel = crearPanelPrincipal("Recursos");
        JPanel fechas = crearFechas(recursosDesde, recursosHasta, this::cargarRecursos);
        panel.add(fechas, BorderLayout.NORTH);
        tablaRecursos.setModel(modelo("Categoría", "Cantidad"));
        JPanel tablaPanel = conTitulo("Estadísticas", new JScrollPane(tablaRecursos));
        panel.add(tablaPanel, BorderLayout.CENTER);
        panel.add(conTitulo("Gráfico", graficoRecursos), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelActividades() {
        JPanel panel = crearPanelPrincipal("Actividades");
        JPanel fechas = crearFechas(actividadesDesde, actividadesHasta, this::cargarActividades);
        panel.add(fechas, BorderLayout.NORTH);
        tablaActividades.setModel(modelo("Semana", "Cantidad"));
        JPanel tablaPanel = conTitulo("Estadísticas", new JScrollPane(tablaActividades));
        panel.add(tablaPanel, BorderLayout.CENTER);
        panel.add(conTitulo("Gráfico", graficoActividades), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelPrincipal(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private JPanel crearFechas(DatePicker desde, DatePicker hasta, Runnable cargar) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Fechas Desde y Hasta"));
        panel.add(new JLabel("Desde"));
        panel.add(desde);
        panel.add(new JLabel("Hasta"));
        panel.add(hasta);
        JButton boton = new JButton("Cargar");
        boton.addActionListener(e -> cargar.run());
        panel.add(boton);
        JButton imprimir = new JButton("Imprimir");
        imprimir.addActionListener(e -> imprimir());
        panel.add(imprimir);
        return panel;
    }

    private JPanel conTitulo(String titulo, Component contenido) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.add(contenido, BorderLayout.CENTER);
        return panel;
    }

    private DefaultTableModel modelo(String primera, String segunda) {
        return new DefaultTableModel(new Object[][]{}, new String[]{primera, segunda}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void cargarTodo() {
        cargarRecursos();
        cargarActividades();
    }

    private void cargarRecursos() {
        if (!rangoValido(recursosDesde, recursosHasta)) {
            return;
        }
        controller.cargarRecursos(recursosDesde.getDate(), recursosHasta.getDate());
    }

    private void cargarActividades() {
        if (!rangoValido(actividadesDesde, actividadesHasta)) {
            return;
        }
        controller.cargarActividades(actividadesDesde.getDate(), actividadesHasta.getDate());
    }

    private boolean rangoValido(DatePicker desde, DatePicker hasta) {
        if (desde.getDate() == null || hasta.getDate() == null) {
            return false;
        }
        if (desde.getDate().isAfter(hasta.getDate())) {
            JOptionPane.showMessageDialog(this, "La fecha inicial no puede ser posterior a la fecha final.",
                    "Estadísticas", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void imprimir() {
        try {
            tablaRecursos.print();
            tablaActividades.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No fue posible imprimir las estadísticas.",
                    "Estadísticas", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ModelEstadisticas.RECURSOS.equals(evt.getPropertyName())) {
            tablaRecursos.setModel(new TableModelEstadisticas(model.getRecursos(), "Categoría"));
            graficoRecursos.setDatos(new ArrayList<>(model.getRecursos().keySet()),
                    new ArrayList<>(model.getRecursos().values()));
        } else if (ModelEstadisticas.ACTIVIDADES.equals(evt.getPropertyName())) {
            tablaActividades.setModel(new TableModelEstadisticas(model.getActividades(), "Semana"));
            graficoActividades.setDatos(new ArrayList<>(model.getActividades().keySet()),
                    new ArrayList<>(model.getActividades().values()));
        }
    }

    private static class GraficoBarras extends JPanel {
        private final Color color;
        private List<String> etiquetas = new ArrayList<>();
        private List<Integer> valores = new ArrayList<>();

        private GraficoBarras(Color color) {
            this.color = color;
            setPreferredSize(new Dimension(0, 260));
            setBackground(Color.WHITE);
        }

        private void setDatos(List<String> etiquetas, List<Integer> valores) {
            this.etiquetas = etiquetas;
            this.valores = valores;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics;
            int left = 45;
            int bottom = getHeight() - 35;
            int top = 25;
            int width = Math.max(1, getWidth() - left - 20);
            int height = Math.max(1, bottom - top);
            int max = valores.stream().mapToInt(Integer::intValue).max().orElse(1);
            int count = Math.max(1, valores.size());
            int slot = width / count;
            for (int i = 0; i < valores.size(); i++) {
                int barHeight = valores.get(i) * height / max;
                int x = left + i * slot + Math.max(4, slot / 5);
                int barWidth = Math.max(8, slot * 3 / 5);
                int y = bottom - barHeight;
                g.setColor(color);
                g.fillRect(x, y, barWidth, barHeight);
                g.setColor(Color.DARK_GRAY);
                g.drawString(String.valueOf(valores.get(i)), x, y - 4);
                String etiqueta = etiquetas.get(i);
                if (etiqueta.length() > 12) {
                    etiqueta = etiqueta.substring(0, 12) + "...";
                }
                g.drawString(etiqueta, x, bottom + 18);
            }
            g.setColor(Color.GRAY);
            g.drawLine(left, top, left, bottom);
            g.drawLine(left, bottom, getWidth() - 15, bottom);
        }
    }
}
