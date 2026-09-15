package Service;

import Recursos.Rol;
import Recursos.User;
import Recursos.Usuarios;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;

public class UserXMLDao {

    private final String ruta = "data/usuarios.xml";

    public UserXMLDao() {
        crearCarpeta();
    }

    public ArrayList<User> listarTodos() {
        try {
            Usuarios usuarios = cargarUsuarios();
            return usuarios.getUsuarios();
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public User buscarPorId(String id) {
        for (User usuario : listarTodos()) {
            if (usuario.getVarId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public ArrayList<User> buscarFuncionarios(String id, String nombre) {
        ArrayList<User> resultado = new ArrayList<>();

        for (User usuario : listarTodos()) {

            if (usuario.getVarRol() != Rol.FUNCIONARIO) {
                continue;
            }

            boolean coincideId =
                    id == null ||
                            id.trim().isEmpty() ||
                            usuario.getVarId().equalsIgnoreCase(id.trim());

            boolean coincideNombre =
                    nombre == null ||
                            nombre.trim().isEmpty() ||
                            usuario.getVarNombre().toLowerCase()
                                    .contains(nombre.trim().toLowerCase());

            if (coincideId && coincideNombre) {
                resultado.add(usuario);
            }
        }

        return resultado;
    }

    public boolean guardarFuncionario(User funcionario) {
        try {
            Usuarios usuarios = cargarUsuarios();

            if (buscarPorIdEnLista(usuarios, funcionario.getVarId()) != null) {
                return false;
            }

            usuarios.agregar(funcionario);
            guardarUsuarios(usuarios);

            return true;

        } catch (Exception e) {
            System.out.println("Error al guardar funcionario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarFuncionario(User funcionario) {
        try {
            Usuarios usuarios = cargarUsuarios();

            User existente =
                    buscarPorIdEnLista(usuarios, funcionario.getVarId());

            if (existente == null) {
                return false;
            }

            int indice = usuarios.getUsuarios().indexOf(existente);

            usuarios.getUsuarios().set(indice, funcionario);

            guardarUsuarios(usuarios);

            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar funcionario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarFuncionario(String id) {
        try {
            Usuarios usuarios = cargarUsuarios();

            User usuario = buscarPorIdEnLista(usuarios, id);

            if (usuario == null) {
                return false;
            }

            usuarios.getUsuarios().remove(usuario);

            guardarUsuarios(usuarios);

            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar funcionario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarClave(String id, String claveNueva) {
        try {
            Usuarios usuarios = cargarUsuarios();

            User usuario = buscarPorIdEnLista(usuarios, id);

            if (usuario == null) {
                return false;
            }

            User usuarioActualizado = new User(
                    usuario.getVarId(),
                    claveNueva,
                    usuario.getVarRol(),
                    usuario.getVarNombre(),
                    usuario.getVarTelefono()
            );

            int indice = usuarios.getUsuarios().indexOf(usuario);

            usuarios.getUsuarios().set(indice, usuarioActualizado);

            guardarUsuarios(usuarios);

            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar clave: " + e.getMessage());
            return false;
        }
    }

    private Usuarios cargarUsuarios() throws Exception {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return new Usuarios();
        }

        JAXBContext context = JAXBContext.newInstance(Usuarios.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Usuarios) unmarshaller.unmarshal(archivo);
    }

    private void guardarUsuarios(Usuarios usuarios) throws Exception {
        File archivo = new File(ruta);

        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        JAXBContext context = JAXBContext.newInstance(Usuarios.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(
                Marshaller.JAXB_FORMATTED_OUTPUT,
                true
        );

        marshaller.setProperty(
                Marshaller.JAXB_ENCODING,
                "UTF-8"
        );

        marshaller.marshal(usuarios, archivo);
    }

    private User buscarPorIdEnLista(Usuarios usuarios, String id) {
        for (User usuario : usuarios.getUsuarios()) {
            if (usuario.getVarId().equals(id)) {
                return usuario;
            }
        }

        return null;
    }

    private void crearCarpeta() {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}