package Presentar.Estadisticas;

import Recursos.Recurso;
import Recursos.Reserva;
import Service.GestorReservas;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasView extends JPanel {
    private final GestorReservas gestor = new GestorReservas();
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
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Reserva reserva : reservasEnRango(recursosDesde, recursosHasta)) {
            for (Recurso recurso : reserva.getRecursos()) {
                String categoria = recurso.getRecurso() == null
                        ? recurso.getCategoriaId() : recurso.getRecurso().getDescripcion();
                conteo.put(categoria, conteo.getOrDefault(categoria, 0) + 1);
            }
        }
        DefaultTableModel model = modelo("Categoría", "Cantidad");
        conteo.forEach((categoria, cantidad) -> model.addRow(new Object[]{categoria, cantidad}));
        tablaRecursos.setModel(model);
        graficoRecursos.setDatos(new ArrayList<>(conteo.keySet()), new ArrayList<>(conteo.values()));
    }

    private void cargarActividades() {
        if (!rangoValido(actividadesDesde, actividadesHasta)) {
            return;
        }
        Map<LocalDate, Integer> conteo = new LinkedHashMap<>();
        for (Reserva reserva : reservasEnRango(actividadesDesde, actividadesHasta)) {
            LocalDate semana = reserva.getFecha()
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            conteo.put(semana, conteo.getOrDefault(semana, 0) + 1);
        }
        DefaultTableModel model = modelo("Semana", "Cantidad");
        conteo.forEach((semana, cantidad) -> model.addRow(new Object[]{semana, cantidad}));
        tablaActividades.setModel(model);
        List<String> etiquetas = conteo.keySet().stream().map(LocalDate::toString).toList();
        graficoActividades.setDatos(etiquetas, new ArrayList<>(conteo.values()));
    }

    private List<Reserva> reservasEnRango(DatePicker desde, DatePicker hasta) {
        List<Reserva> resultado = new ArrayList<>();
        gestor.recargarReservas();
        for (Reserva reserva : gestor.getReservas()) {
            if (!reserva.getFecha().isBefore(desde.getDate())
                    && !reserva.getFecha().isAfter(hasta.getDate())) {
                resultado.add(reserva);
            }
        }
        return resultado;
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
