package Service;

import Recursos.CategoriaRecurso;
import Recursos.Recurso;
import Recursos.Reserva;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import Recursos.Reserva;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Recursos.User;
import java.util.List;
import java.util.Map;

public class PDFService {

    public static void generarPDFReservas(ArrayList<Reserva> reservas) throws Exception {
        String dest = "reservas.pdf";

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Listado de Reservas")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        Table tabla = new Table(6);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("ID").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Actividad").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Fecha").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Hora inicio").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Hora fin").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Recursos").setBold()));

        for (Reserva reserva : reservas) {

            tabla.addCell(new Cell().add(
                    new Paragraph(reserva.getId())
            ));

            tabla.addCell(new Cell().add(
                    new Paragraph(reserva.getActividad())
            ));

            tabla.addCell(new Cell().add(
                    new Paragraph(
                            reserva.getFecha() != null
                                    ? reserva.getFecha().format(
                                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                    : ""
                    )
            ));

            tabla.addCell(new Cell().add(
                    new Paragraph(
                            reserva.getHoraInicio() != null
                                    ? reserva.getHoraInicio().format(
                                    java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
                                    : ""
                    )
            ));

            tabla.addCell(new Cell().add(
                    new Paragraph(
                            reserva.getHoraFin() != null
                                    ? reserva.getHoraFin().format(
                                    java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
                                    : ""
                    )
            ));

            tabla.addCell(new Cell().add(
                    new Paragraph(
                            reserva.getRecursos() != null
                                    ? String.valueOf(reserva.getRecursos().size())
                                    : "0"
                    )
            ));
        }

        document.add(tabla);

        document.close();

        openPdf(dest);
    }
    public static void generarPDFCalendarizacion(ArrayList<Reserva> reservas, LocalDate fecha, CategoriaRecurso categoria) throws Exception {
        String dest = "calendarizacion.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Calendarización de Reservas")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        String fechaTexto = fecha != null
                ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "No seleccionada";

        String categoriaTexto = categoria != null
                ? categoria.getDescripcion()
                : "Todas";

        Paragraph informacion = new Paragraph(
                "Fecha: " + fechaTexto + "    Categoría: " + categoriaTexto
        ).setFont(font).setFontSize(11);

        document.add(informacion);

        Table tabla = new Table(6);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("ID").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Actividad").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Fecha").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Hora inicio").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Hora fin").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Recursos").setBold()));

        for (Reserva reserva : reservas) {
            tabla.addCell(new Cell().add(new Paragraph(reserva.getId())));
            tabla.addCell(new Cell().add(new Paragraph(reserva.getActividad())));
            tabla.addCell(new Cell().add(new Paragraph(
                    reserva.getFecha() != null
                            ? reserva.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            : ""
            )));
            tabla.addCell(new Cell().add(new Paragraph(
                    reserva.getHoraInicio() != null
                            ? reserva.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm"))
                            : ""
            )));
            tabla.addCell(new Cell().add(new Paragraph(
                    reserva.getHoraFin() != null
                            ? reserva.getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm"))
                            : ""
            )));
            tabla.addCell(new Cell().add(new Paragraph(
                    reserva.getRecursos() != null
                            ? String.valueOf(reserva.getRecursos().size())
                            : "0"
            )));
        }

        document.add(tabla);
        document.close();
        openPdf(dest);
    }

    private static void openPdf(String path) {
        try {
            File pdfFile = new File(path);

            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Desktop no está disponible.");
                }
            } else {
                System.out.println("El archivo PDF no existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void generarPDFCategorias(ArrayList<CategoriaRecurso> categorias) throws Exception {
        String dest = "categorias.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Listado de Categorías")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        Table tabla = new Table(2);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("ID").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Descripción").setBold()));

        for (CategoriaRecurso categoria : categorias) {
            tabla.addCell(new Cell().add(new Paragraph(categoria.getVarId())));
            tabla.addCell(new Cell().add(new Paragraph(categoria.getDescripcion())));
        }

        document.add(tabla);
        document.close();
        openPdf(dest);
    }
    public static void generarPDFRecursos(List<Recurso> recursos) throws Exception {
        String dest = "recursos.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Listado de Recursos")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        Table tabla = new Table(3);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("ID").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Categoría").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Descripción").setBold()));

        for (Recurso recurso : recursos) {
            String categoria = recurso.getRecurso() != null
                    ? recurso.getRecurso().getDescripcion()
                    : recurso.getCategoriaId();

            tabla.addCell(new Cell().add(new Paragraph(recurso.getId())));
            tabla.addCell(new Cell().add(new Paragraph(categoria)));
            tabla.addCell(new Cell().add(new Paragraph(recurso.getDescripcion())));
        }

        document.add(tabla);
        document.close();
        openPdf(dest);
    }
    public static void generarPDFActividades(LocalDate lunes, List<Reserva> reservas) throws Exception {
        String dest = "actividades.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Calendarización de Actividades")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        Table tabla = new Table(8);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("Hora").setBold()));

        for (int dia = 0; dia < 7; dia++) {
            LocalDate fecha = lunes.plusDays(dia);
            tabla.addCell(new Cell().add(new Paragraph(fecha.format(formatoFecha)).setBold()));
        }

        for (int hora = 0; hora < 24; hora++) {
            tabla.addCell(new Cell().add(new Paragraph(String.format("%02d:00", hora))));

            for (int dia = 0; dia < 7; dia++) {
                LocalDate fecha = lunes.plusDays(dia);
                String actividad = "";

                for (Reserva reserva : reservas) {
                    if (reserva.getFecha() != null
                            && reserva.getFecha().equals(fecha)
                            && reserva.getHoraInicio() != null
                            && reserva.getHoraFin() != null
                            && reserva.getHoraInicio().isBefore(hora == 23 ? LocalTime.of(23, 59, 59) : LocalTime.of(hora + 1, 0))
                            && reserva.getHoraFin().isAfter(LocalTime.of(hora, 0))) {

                        String usuario = reserva.getUsuario() != null
                                && reserva.getUsuario().getVarNombre() != null
                                && !reserva.getUsuario().getVarNombre().isBlank()
                                ? reserva.getUsuario().getVarNombre()
                                : reserva.getUsuarioId();

                        actividad = reserva.getActividad() + " (" + usuario + ")";
                        break;
                    }
                }

                tabla.addCell(new Cell().add(new Paragraph(actividad)));
            }
        }

        document.add(tabla);
        document.close();
        openPdf(dest);
    }
    public static void generarPDFFuncionarios(List<User> funcionarios) throws Exception {
        String dest = "funcionarios.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Listado de Funcionarios")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        Table tabla = new Table(3);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("ID").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Nombre").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Teléfono").setBold()));

        for (User funcionario : funcionarios) {
            tabla.addCell(new Cell().add(new Paragraph(funcionario.getVarId())));
            tabla.addCell(new Cell().add(new Paragraph(funcionario.getVarNombre())));
            tabla.addCell(new Cell().add(new Paragraph(funcionario.getVarTelefono())));
        }

        document.add(tabla);
        document.close();

        openPdf(dest);
    }
    public static void generarPDFEstadisticasRecursos(Map<String, Integer> recursos) throws Exception {
        String dest = "estadisticas_recursos.pdf";
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        Paragraph titulo = new Paragraph("Estadísticas de Recursos")
                .setFont(font)
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);

        document.add(titulo);

        Table tabla = new Table(2);
        tabla.setWidth(550);

        tabla.addCell(new Cell().add(new Paragraph("Categoría").setBold()));
        tabla.addCell(new Cell().add(new Paragraph("Cantidad").setBold()));

        for (Map.Entry<String, Integer> entrada : recursos.entrySet()) {
            tabla.addCell(new Cell().add(new Paragraph(entrada.getKey())));
            tabla.addCell(new Cell().add(new Paragraph(String.valueOf(entrada.getValue()))));
        }

        document.add(tabla);
        document.close();

        openPdf(dest);
    }
}