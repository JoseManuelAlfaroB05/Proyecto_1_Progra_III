package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name = "usuarios")
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuarios {

    @XmlElement(name = "usuario")
    private ArrayList<User> usuarios;

    public Usuarios() {
        usuarios = new ArrayList<>();
    }

    public ArrayList<User> getUsuarios() {
        return usuarios;
    }

    public void agregar(User usuario) {
        usuarios.add(usuario);
    }
}