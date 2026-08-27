package Controllers;

import Models.User;
import Persistencia.UserXMLDao;

public class ControllerLogin {

    private UserXMLDao dao = new UserXMLDao();

    public User autenticar(String id, String claveIngresada) {
        User usuario = dao.buscarPorId(id);

        if (usuario != null && usuario.getVarClave().equals(claveIngresada)) {
            return usuario;
        }
        return null;
    }
}