package Persistencia;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import Login.User;
import Login.Rol;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UserXMLDao {
    private String rutaArchivo = "data/usuarios.xml";
    public List<User> listarTodos() {
        List<User> usuarios = new ArrayList<>();

        try {
            File archivo = new File(rutaArchivo);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaNodos = doc.getElementsByTagName("usuario");

            for (int i = 0; i < listaNodos.getLength(); i++) {
                Element elemento = (Element) listaNodos.item(i);

                String id = elemento.getElementsByTagName("id").item(0).getTextContent();
                String clave = elemento.getElementsByTagName("clave").item(0).getTextContent();
                String rolTexto = elemento.getElementsByTagName("rol").item(0).getTextContent();
                Rol rol = Rol.valueOf(rolTexto);

                usuarios.add(new User(id, clave, rol));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean actualizarClave(String id, String claveNueva) {
        try{
            File archivo = new File(rutaArchivo);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaNodos = doc.getElementsByTagName("usuario");

            for (int i = 0; i < listaNodos.getLength(); i++) {
                Element elemento = (Element) listaNodos.item(i);
                String idActual = elemento.getElementsByTagName("id").item(0).getTextContent();

                if (idActual.equals(id)) {
                    elemento.getElementsByTagName("clave").item(0).setTextContent(claveNueva);

                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.transform(new DOMSource(doc), new StreamResult(archivo));

                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public User buscarPorId(String id) {
        List<User> usuarios = listarTodos();
        for (User u : usuarios) {
            if (u.getVarId().equals(id)) {
                return u;
            }
        }
        return null;
    }
}