package Service;

import Models.CategoriaRecurso;
import Models.Recurso;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursoXMLDao {

    private String rutaArchivo = "data/recursos.xml";

    public List<Recurso> listarTodos() {

        List<Recurso> recursos = new ArrayList<>();

        try {

            File archivo = new File(rutaArchivo);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaNodos = doc.getElementsByTagName("recurso");

            for (int i = 0; i < listaNodos.getLength(); i++) {

                Element elemento = (Element) listaNodos.item(i);

                String id = elemento
                        .getElementsByTagName("id")
                        .item(0)
                        .getTextContent();

                String categoriaId = elemento
                        .getElementsByTagName("categoria")
                        .item(0)
                        .getTextContent();

                String descripcion = elemento
                        .getElementsByTagName("descripcion")
                        .item(0)
                        .getTextContent();

                CategoriaRecurso categoria =
                        obtenerCategoria(categoriaId);

                recursos.add(
                        new Recurso(id, categoria, descripcion)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recursos;
    }

    private CategoriaRecurso obtenerCategoria(String categoriaId) {

        switch (categoriaId) {

            case "LAB":
                return new CategoriaRecurso(
                        "LAB",
                        "Laboratorio"
                );

            case "PC":
                return new CategoriaRecurso(
                        "PC",
                        "Computadora"
                );

            case "PRO":
                return new CategoriaRecurso(
                        "PRO",
                        "Proyector"
                );

            default:
                return null;
        }
    }
}