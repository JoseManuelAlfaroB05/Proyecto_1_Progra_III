package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @XmlElement(name = "id")
    private String varId;

    @XmlElement(name = "clave")
    private String varClave;

    @XmlElement(name = "rol")
    private Rol varRol;

    @XmlElement(name = "nombre")
    private String varNombre;

    @XmlElement(name = "telefono")
    private String varTelefono;

    public User() {
    }

    public User(String id, String clave, Rol rol) {
        this(id, clave, rol, "", "");
    }

    public User(String id, String clave, Rol rol, String nombre, String telefono) {
        this.varId = id;
        this.varClave = clave;
        this.varRol = rol;
        this.varNombre = nombre;
        this.varTelefono = telefono;
    }

    public String getVarId() {
        return this.varId;
    }

    public String getVarClave() {
        return this.varClave;
    }

    public Rol getVarRol() {
        return this.varRol;
    }

    public String getVarNombre() {
        return varNombre;
    }

    public String getVarTelefono() {
        return varTelefono;
    }
}