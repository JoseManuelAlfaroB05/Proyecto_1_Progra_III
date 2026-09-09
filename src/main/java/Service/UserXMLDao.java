package Service;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Recursos.User;
import Recursos.Rol;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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

                String nombre = obtenerTexto(elemento, "nombre");
                String telefono = obtenerTexto(elemento, "telefono");
                usuarios.add(new User(id, clave, rol, nombre, telefono));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean actualizarClave(String id, String claveNueva) {
        try {
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
        } catch (Exception e) {
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

    public List<User> buscarFuncionarios(String id, String nombre) {
        List<User> resultado = new ArrayList<>();
        String idFiltro = id == null ? "" : id.trim().toLowerCase(Locale.ROOT);
        String nombreFiltro = nombre == null ? "" : nombre.trim().toLowerCase(Locale.ROOT);

        for (User usuario : listarTodos()) {
            if (usuario.getVarRol() != Rol.FUNCIONARIO) {
                continue;
            }
            boolean coincideId = !idFiltro.isEmpty()
                    && usuario.getVarId().toLowerCase(Locale.ROOT).startsWith(idFiltro);
            boolean coincideNombre = !nombreFiltro.isEmpty()
                    && usuario.getVarNombre().toLowerCase(Locale.ROOT).startsWith(nombreFiltro);
            if ((idFiltro.isEmpty() || coincideId)
                    && (nombreFiltro.isEmpty() || coincideNombre)) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public boolean guardarFuncionario(User funcionario) {
        try {
            Document doc = leerDocumento();
            if (buscarPorIdEnDocumento(doc, funcionario.getVarId()) != null) {
                return false;
            }

            Element usuario = doc.createElement("usuario");
            agregarTexto(doc, usuario, "id", funcionario.getVarId());
            agregarTexto(doc, usuario, "clave", funcionario.getVarClave());
            agregarTexto(doc, usuario, "rol", funcionario.getVarRol().name());
            agregarTexto(doc, usuario, "nombre", funcionario.getVarNombre());
            agregarTexto(doc, usuario, "telefono", funcionario.getVarTelefono());
            doc.getDocumentElement().appendChild(usuario);
            escribirDocumento(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarFuncionario(User funcionario) {
        try {
            Document doc = leerDocumento();
            Element usuario = buscarPorIdEnDocumento(doc, funcionario.getVarId());
            if (usuario == null || !Rol.FUNCIONARIO.name().equals(
                    obtenerTexto(usuario, "rol"))) {
                return false;
            }
            establecerTexto(usuario, "nombre", funcionario.getVarNombre());
            establecerTexto(usuario, "telefono", funcionario.getVarTelefono());
            escribirDocumento(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarFuncionario(String id) {
        try {
            Document doc = leerDocumento();
            Element usuario = buscarPorIdEnDocumento(doc, id);
            if (usuario == null || !Rol.FUNCIONARIO.name().equals(
                    obtenerTexto(usuario, "rol"))) {
                return false;
            }
            usuario.getParentNode().removeChild(usuario);
            escribirDocumento(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Document leerDocumento() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(rutaArchivo));
        doc.getDocumentElement().normalize();
        return doc;
    }

    private Element buscarPorIdEnDocumento(Document doc, String id) {
        NodeList nodos = doc.getElementsByTagName("usuario");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element usuario = (Element) nodos.item(i);
            if (id.equals(obtenerTexto(usuario, "id"))) {
                return usuario;
            }
        }
        return null;
    }

    private static String obtenerTexto(Element elemento, String etiqueta) {
        NodeList nodos = elemento.getElementsByTagName(etiqueta);
        return nodos.getLength() == 0 ? "" : nodos.item(0).getTextContent();
    }

    private static void agregarTexto(Document doc, Element padre, String etiqueta, String valor) {
        Element elemento = doc.createElement(etiqueta);
        elemento.setTextContent(valor == null ? "" : valor);
        padre.appendChild(elemento);
    }

    private static void establecerTexto(Element padre, String etiqueta, String valor) {
        NodeList nodos = padre.getElementsByTagName(etiqueta);
        if (nodos.getLength() == 0) {
            agregarTexto(padre.getOwnerDocument(), padre, etiqueta, valor);
        } else {
            nodos.item(0).setTextContent(valor == null ? "" : valor);
        }
    }

    private void escribirDocumento(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(rutaArchivo)));
    }
}