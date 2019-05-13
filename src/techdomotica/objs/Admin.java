package techdomotica.objs;

public class Admin extends Usuario {
    
    private Perfil perfilActual = null;
    
    public Admin(String id_usu, String name1, String name2, String ape1, String ape2, String corre, String doc, String con) {
        super(id_usu, name1, name2, ape1, ape2, corre, doc, con);
        loadDefaultPerfil();
    }

    public void loadDefaultPerfil() {
        Conectar conx = new Conectar();
        if(conx.executeRSOne("SELECT usuario.perfil_actual, perfil.* FROM usuario INNER JOIN perfil ON usuario.perfil_actual = perfil.id_perfil WHERE perfil.id_usuario = " + getID() + " AND perfil.habilitado = 1;")) {
            perfilActual = new Perfil(Util.parseInteger(conx.getResultSetRow("id_perfil")), Util.parseInteger(conx.getResultSetRow("temp1")), Util.parseInteger(conx.getResultSetRow("temp2")), (Util.parseInteger(conx.getResultSetRow("temp1_on")) == 1), (Util.parseInteger(conx.getResultSetRow("temp2_on")) == 1), (Util.parseInteger(conx.getResultSetRow("proyector_on")) == 1));
        }
        if(conx.getResultSet() != null) conx.destroyResultSet();
        conx.closeConnection();
    }
    
    public Perfil getPerfilActual() {
        return perfilActual;
    }
    
}