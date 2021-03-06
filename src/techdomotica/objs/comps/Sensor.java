package techdomotica.objs.comps;

public class Sensor extends Componente {
    
    private String tipoSensor = "";
    private String ubicacion = "";
    
    public Sensor(String nombre, String marca, double uso) {
        super(nombre, marca, uso);
    }
    
    public Sensor(String nombre, String marca) {
        super(nombre, marca);
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    
    
}
