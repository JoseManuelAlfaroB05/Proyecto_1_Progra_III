package Controllers;

import Models.Rol;
import Models.User;
import Persistencia.UserXMLDao;

import java.util.List;

public class ControllerFuncionario {
    private final UserXMLDao dao = new UserXMLDao();

    public List<User> buscar(String id, String nombre) {
        return dao.buscarFuncionarios(id, nombre);
    }

    public boolean guardar(String id, String nombre, String telefono) {
        if (!telefonoValido(telefono)) {
            return false;
        }
        return dao.guardarFuncionario(new User(id, id, Rol.FUNCIONARIO, nombre, telefono));
    }

    public boolean actualizar(String id, String nombre, String telefono) {
        if (!telefonoValido(telefono)) {
            return false;
        }
        return dao.actualizarFuncionario(new User(id, "", Rol.FUNCIONARIO, nombre, telefono));
    }

    public boolean eliminar(String id) {
        return dao.eliminarFuncionario(id);
    }

    private boolean telefonoValido(String telefono) {
        return telefono != null && telefono.matches("[0-9]+");
    }
}
