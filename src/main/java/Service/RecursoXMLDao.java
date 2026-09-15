package Service;

import Recursos.Recurso;
import Recursos.Recursos;
import Recursos.CategoriaRecurso;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;

public class RecursoXMLDao {

    private final String ruta = "data/recursos.xml";
    private final GestorCategorias gestorCategorias;

    public RecursoXMLDao() {
        gestorCategorias = new GestorCategorias();
    }

    public ArrayList<Recurso> listarTodos() {
        try {
            File archivo = new File(ruta);

            if (!archivo.exists()) {
                return new ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(Recursos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Recursos recursos = (Recursos) unmarshaller.unmarshal(archivo);

            ArrayList<Recurso> resultado = recursos.getRecursos();

            for (Recurso recurso : resultado) {
                CategoriaRecurso categoria =
                        gestorCategorias.buscarPorId(recurso.getCategoriaId());

                recurso.setRecurso(categoria);
            }

            return resultado;

        } catch (Exception e) {
            System.out.println("Error al cargar recursos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean guardar(Recurso recurso) {
        try {
            Recursos recursos = cargarRecursos();
            recursos.agregar(recurso);
            guardarRecursos(recursos);
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar recurso: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Recurso recurso) {
        try {
            Recursos recursos = cargarRecursos();
            for (int i = 0; i < recursos.getRecursos().size(); i++) {
                Recurso existente = recursos.getRecursos().get(i);
                if (existente.getId().equalsIgnoreCase(recurso.getId())) {
                    recursos.getRecursos().set(i, recurso);
                    guardarRecursos(recursos);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al actualizar recurso: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        try {
            Recursos recursos = cargarRecursos();
            boolean eliminado = recursos.getRecursos()
                    .removeIf(recurso -> recurso.getId().equalsIgnoreCase(id));
            if (eliminado) {
                guardarRecursos(recursos);
            }
            return eliminado;
        } catch (Exception e) {
            System.out.println("Error al eliminar recurso: " + e.getMessage());
            return false;
        }
    }

    private Recursos cargarRecursos() throws Exception {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return new Recursos();
        }

        JAXBContext context = JAXBContext.newInstance(Recursos.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Recursos recursos = (Recursos) unmarshaller.unmarshal(archivo);

        for (Recurso recurso : recursos.getRecursos()) {
            CategoriaRecurso categoria =
                    gestorCategorias.buscarPorId(recurso.getCategoriaId());

            recurso.setRecurso(categoria);
        }

        return recursos;
    }

    private void guardarRecursos(Recursos recursos) throws Exception {
        File archivo = new File(ruta);

        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        JAXBContext context = JAXBContext.newInstance(Recursos.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        marshaller.marshal(recursos, archivo);
    }
}