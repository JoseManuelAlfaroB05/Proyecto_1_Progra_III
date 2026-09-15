package Service;

import Recursos.Recurso;
import Recursos.Reserva;
import Recursos.Reservas;
import Recursos.User;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;

public class ReservaXMLDao {

    private final String ruta = "data/reservas.xml";

    public ReservaXMLDao() {
        crearCarpeta();
    }

    public ArrayList<Reserva> cargar() {
        try {
            File archivo = new File(ruta);

            if (!archivo.exists()) {
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(Reservas.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Reservas reservas =
                    (Reservas) unmarshaller.unmarshal(archivo);

            ArrayList<Reserva> resultado = reservas.getReservas();

            cargarRelaciones(resultado);

            return resultado;

        } catch (Exception e) {
            System.out.println("Error al cargar reservas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean guardar(Reserva reserva) {
        try {
            Reservas reservas = cargarReservas();

            reservas.agregar(reserva);

            guardarReservas(reservas);

            return true;

        } catch (Exception e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
            return false;
        }
    }

    private Reservas cargarReservas() throws Exception {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return new Reservas();
        }

        JAXBContext context = JAXBContext.newInstance(Reservas.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Reservas reservas =
                (Reservas) unmarshaller.unmarshal(archivo);

        cargarRelaciones(reservas.getReservas());

        return reservas;
    }

    private void guardarReservas(Reservas reservas) throws Exception {
        File archivo = new File(ruta);

        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        actualizarIds(reservas);

        JAXBContext context = JAXBContext.newInstance(Reservas.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(
                Marshaller.JAXB_FORMATTED_OUTPUT,
                true
        );

        marshaller.setProperty(
                Marshaller.JAXB_ENCODING,
                "UTF-8"
        );

        marshaller.marshal(reservas, archivo);
    }

    private void cargarRelaciones(ArrayList<Reserva> reservas) {

        UserXMLDao userDAO = new UserXMLDao();
        RecursoXMLDao recursoDAO = new RecursoXMLDao();

        ArrayList<User> usuarios = userDAO.listarTodos();
        ArrayList<Recurso> recursos = recursoDAO.listarTodos();

        for (Reserva reserva : reservas) {

            User usuario = null;

            for (User user : usuarios) {
                if (user.getVarId().equals(reserva.getUsuarioId())) {
                    usuario = user;
                    break;
                }
            }

            reserva.setUsuario(usuario);

            ArrayList<Recurso> recursosReserva = new ArrayList<>();

            for (String recursoId : reserva.getRecursosIds()) {

                for (Recurso recurso : recursos) {

                    if (recurso.getId().equals(recursoId)) {
                        recursosReserva.add(recurso);
                        break;
                    }
                }
            }

            reserva.setRecursos(recursosReserva);
        }
    }

    private void actualizarIds(Reservas reservas) {

        for (Reserva reserva : reservas.getReservas()) {

            if (reserva.getUsuario() != null) {
                reserva.setUsuario(reserva.getUsuario());
            }

            if (reserva.getRecursos() != null) {
                reserva.setRecursos(reserva.getRecursos());
            }
        }
    }

    private void crearCarpeta() {

        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}