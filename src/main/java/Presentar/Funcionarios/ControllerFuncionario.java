package Presentar.Funcionarios;

import Recursos.Rol;
import Recursos.User;
import Service.UserXMLDao;

import java.util.List;

public class ControllerFuncionario {
    private final UserXMLDao dao = new UserXMLDao();
    private final ModelFuncionario model = new ModelFuncionario();

    public ModelFuncionario getModel() {
        return model;
    }

    public List<User> buscar(String id, String nombre) {
        return dao.buscarFuncionarios(id, nombre);
    }

    public boolean guardar(String id, String nombre, String telefono) {
        model.setId(id);
        model.setNombre(nombre);
        model.setTelefono(telefono);

        if (!telefonoValido(model.getTelefono())) {
            return false;
        }

        return dao.guardarFuncionario(
                new User(
                        model.getId(),
                        model.getId(),
                        Rol.FUNCIONARIO,
                        model.getNombre(),
                        model.getTelefono()
                )
        );
    }

    public boolean actualizar(String id, String nombre, String telefono) {
        model.setId(id);
        model.setNombre(nombre);
        model.setTelefono(telefono);

        if (!telefonoValido(model.getTelefono())) {
            return false;
        }

        return dao.actualizarFuncionario(
                new User(
                        model.getId(),
                        "",
                        Rol.FUNCIONARIO,
                        model.getNombre(),
                        model.getTelefono()
                )
        );
    }

    public boolean eliminar(String id) {
        return dao.eliminarFuncionario(id);
    }

    private boolean telefonoValido(String telefono) {
        return telefono != null && telefono.matches("[0-9]+");
    }
}