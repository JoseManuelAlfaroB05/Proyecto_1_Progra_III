package Service;

import Models.Recurso;
import Models.Reserva;
import Models.User;
import Models.Rol;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

public class ReservaXMLDao {

    private final String ruta = "data/reservas.xml";

    public void guardar(Reserva reserva) {

        try {

            File archivo = new File(ruta);
            File carpeta = archivo.getParentFile();

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document documento;

            if (archivo.exists() && archivo.length() > 0) {
                documento = builder.parse(archivo);
            } else {
                documento = builder.newDocument();

                Element raiz = documento.createElement("reservas");
                documento.appendChild(raiz);
            }

            Element raiz = documento.getDocumentElement();

            Element elementoReserva = documento.createElement("reserva");
            raiz.appendChild(elementoReserva);

            Element id = documento.createElement("id");
            id.setTextContent(reserva.getId());
            elementoReserva.appendChild(id);

            Element usuario = documento.createElement("usuario");
            usuario.setTextContent(reserva.getUsuario().getVarId());
            elementoReserva.appendChild(usuario);

            Element actividad = documento.createElement("actividad");
            actividad.setTextContent(reserva.getActividad());
            elementoReserva.appendChild(actividad);

            Element fecha = documento.createElement("fecha");
            fecha.setTextContent(reserva.getFecha().toString());
            elementoReserva.appendChild(fecha);

            Element horaInicio = documento.createElement("horaInicio");
            horaInicio.setTextContent(reserva.getHoraInicio().toString());
            elementoReserva.appendChild(horaInicio);

            Element horaFin = documento.createElement("horaFin");
            horaFin.setTextContent(reserva.getHoraFin().toString());
            elementoReserva.appendChild(horaFin);

            Element recursos = documento.createElement("recursos");
            elementoReserva.appendChild(recursos);

            for (Recurso recurso : reserva.getRecursos()) {

                Element elementoRecurso =
                        documento.createElement("recurso");

                elementoRecurso.setTextContent(recurso.getId());

                recursos.appendChild(elementoRecurso);
            }

            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();

            Transformer transformer =
                    transformerFactory.newTransformer();

            transformer.setOutputProperty(
                    OutputKeys.INDENT,
                    "yes"
            );

            transformer.setOutputProperty(
                    OutputKeys.ENCODING,
                    "UTF-8"
            );

            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult(archivo);

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Reserva> cargar() {

        ArrayList<Reserva> reservas = new ArrayList<>();

        try {

            File archivo = new File(ruta);

            if (!archivo.exists()) {
                return reservas;
            }

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document documento = builder.parse(archivo);

            documento.getDocumentElement().normalize();

            NodeList listaReservas =
                    documento.getElementsByTagName("reserva");

            GestorRecursos gestorRecursos =
                    new GestorRecursos();

            for (int i = 0; i < listaReservas.getLength(); i++) {

                Element elemento =
                        (Element) listaReservas.item(i);

                String id =
                        elemento
                                .getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                String usuarioId =
                        elemento
                                .getElementsByTagName("usuario")
                                .item(0)
                                .getTextContent();

                User usuario =
                        new User(
                                usuarioId,
                                "",
                                Rol.FUNCIONARIO
                        );

                String actividad =
                        elemento
                                .getElementsByTagName("actividad")
                                .item(0)
                                .getTextContent();

                LocalDate fecha =
                        LocalDate.parse(
                                elemento
                                        .getElementsByTagName("fecha")
                                        .item(0)
                                        .getTextContent()
                        );

                LocalTime horaInicio =
                        LocalTime.parse(
                                elemento
                                        .getElementsByTagName("horaInicio")
                                        .item(0)
                                        .getTextContent()
                        );

                LocalTime horaFin =
                        LocalTime.parse(
                                elemento
                                        .getElementsByTagName("horaFin")
                                        .item(0)
                                        .getTextContent()
                        );

                ArrayList<Recurso> recursos =
                        new ArrayList<>();

                NodeList listaRecursos =
                        elemento
                                .getElementsByTagName("recurso");

                for (int j = 0; j < listaRecursos.getLength(); j++) {

                    String idRecurso =
                            listaRecursos
                                    .item(j)
                                    .getTextContent();

                    Recurso recurso =
                            gestorRecursos.buscarPorId(idRecurso);

                    if (recurso != null) {
                        recursos.add(recurso);
                    }
                }

                Reserva reserva = new Reserva(
                        id,
                        usuario,
                        actividad,
                        fecha,
                        horaInicio,
                        horaFin,
                        recursos
                );

                reservas.add(reserva);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservas;
    }
}