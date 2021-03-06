package techdomotica.objs;

public class Usuario {
    
    private String nombre1 = "",
                   nombre2 = "",
                   apellido1 = "",
                   apellido2 = "",
                   correo = "",
                   documento = "",
                   Contraseña = "",
                   id = "";
    
    public Usuario(String id_usu, String name1, String name2, String ape1, String ape2, String corre, String doc, String contra) {
        nombre1 = name1;
        nombre2 = name2;
        apellido1 = ape1;
        apellido2 = ape2;
        correo = corre;
        documento = doc;
        Contraseña = contra;
        id = id_usu;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getFullName() {
        return nombre1 + " " +  nombre2 + " " + apellido1 + " " + apellido2;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }
    
    public String getID() {
        return id;
    }
    
    public static int insertUser(int rol, String correo, String contra, String nombre1, String nombre2, String apellido1, String apellido2, String documento) {
        Conectar conx = new Conectar();
        String hashed = techdomotica.objs.Util.SHA256(contra);
        return conx.execute(String.format("INSERT INTO `usuario` VALUES (null, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', 1);", rol, correo, hashed, nombre1, nombre2, apellido1, apellido2, documento));
    }
    
    public static int modifyUser(int rol, String correo, String nombre1, String nombre2, String apellido1, String apellido2, String documento, int modifiedID, boolean enabled) {
        Conectar conx = new Conectar();
        return conx.execute(String.format("UPDATE usuario SET id_rol = %d, correo = '%s', nom1 = '%s', nom2 = '%s', apellido1 = '%s', apellido2 = '%s', dni = '%s', habilitado = %d WHERE id_usuario = %d;", rol, correo, nombre1, nombre2, apellido1, apellido2, documento, (enabled) ? 1 : 0, modifiedID));
    }
    
    
}