package Service;

import Recursos.CategoriaRecurso;
import Recursos.Categorias;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class CategoriaXMLDao {

    private final String ruta = "data/categorias.xml";

    public CategoriaXMLDao() {
        crearCarpeta();
    }

    public java.util.ArrayList<CategoriaRecurso> listarTodas() {
        try {
            File archivo = new File(ruta);

            if (!archivo.exists()) {
                return new java.util.ArrayList<>();
            }

            JAXBContext context = JAXBContext.newInstance(Categorias.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Categorias categorias = (Categorias) unmarshaller.unmarshal(archivo);

            return categorias.getCategorias();

        } catch (Exception e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public boolean guardar(CategoriaRecurso categoria) {
        try {
            Categorias categorias = cargarCategorias();

            categorias.agregar(categoria);

            guardarCategorias(categorias);

            return true;

        } catch (Exception e) {
            System.out.println("Error al guardar categoría: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCategoria(String id) {
        try {
            Categorias categorias = cargarCategorias();

            CategoriaRecurso categoriaEliminar = null;

            for (CategoriaRecurso categoria : categorias.getCategorias()) {
                if (categoria.getVarId().equals(id)) {
                    categoriaEliminar = categoria;
                    break;
                }
            }

            if (categoriaEliminar == null) {
                return false;
            }

            categorias.getCategorias().remove(categoriaEliminar);

            guardarCategorias(categorias);

            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }

    private Categorias cargarCategorias() throws Exception {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return new Categorias();
        }

        JAXBContext context = JAXBContext.newInstance(Categorias.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Categorias) unmarshaller.unmarshal(archivo);
    }

    private void guardarCategorias(Categorias categorias) throws Exception {
        File archivo = new File(ruta);

        JAXBContext context = JAXBContext.newInstance(Categorias.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        marshaller.marshal(categorias, archivo);
    }

    private void crearCarpeta() {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}