package Login;

public class User {
    private String varId;
    private String varClave;
    private Rol varRol;

    public User(String id, String clave, Rol rol){
        this.varId = id;
        this.varClave = clave;
        this.varRol = rol;
    }

    public String getVarId(){
        return this.varId;
    }

    public String getVarClave(){
        return this.varClave;
    }

    public Rol getVarRol(){
        return this.varRol;
    }
}