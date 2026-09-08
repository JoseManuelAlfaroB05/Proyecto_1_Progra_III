package Service;

import Models.CategoriaRecurso;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class CategoriaXMLDao {

    private final String ruta = "data/categorias.xml";

    public ArrayList<CategoriaRecurso> listarTodas() {

        ArrayList<CategoriaRecurso> categorias = new ArrayList<>();

        try {

            File archivo = new File(ruta);

            if (!archivo.exists()) {
                return categorias;
            }

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document documento = builder.parse(archivo);

            documento.getDocumentElement().normalize();

            NodeList lista =
                    documento.getElementsByTagName("categoria");

            for (int i = 0; i < lista.getLength(); i++) {

                Element elemento =
                        (Element) lista.item(i);

                String id =
                        elemento
                                .getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                String descripcion =
                        elemento
                                .getElementsByTagName("descripcion")
                                .item(0)
                                .getTextContent();

                categorias.add(
                        new CategoriaRecurso(id, descripcion)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public boolean guardar(CategoriaRecurso categoria) {

        try {

            File archivo = new File(ruta);
            File carpeta = archivo.getParentFile();

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document documento;

            if (archivo.exists() && archivo.length() > 0) {
                documento = builder.parse(archivo);
            } else {

                documento = builder.newDocument();

                Element raiz =
                        documento.createElement("categorias");

                documento.appendChild(raiz);
            }

            Element raiz =
                    documento.getDocumentElement();

            Element elementoCategoria =
                    documento.createElement("categoria");

            Element id =
                    documento.createElement("id");

            id.setTextContent(categoria.getVarId());

            Element descripcion =
                    documento.createElement("descripcion");

            descripcion.setTextContent(
                    categoria.getDescripcion()
            );

            elementoCategoria.appendChild(id);
            elementoCategoria.appendChild(descripcion);

            raiz.appendChild(elementoCategoria);

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

            DOMSource source =
                    new DOMSource(documento);

            StreamResult result =
                    new StreamResult(archivo);

            transformer.transform(source, result);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}