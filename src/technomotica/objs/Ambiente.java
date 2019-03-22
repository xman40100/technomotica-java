package technomotica.objs;

import technomotica.objs.comps.*;

public class Ambiente {
    
    private Thread ambienteThread;
    private boolean continueThread = true;
    
    private String perfilActual = "";
    private Admin adminEncargado = null;
    
    private double temperaturaSala = 0.0;

    private ACondicionado[] acondicionado = new ACondicionado[2];
    private Camara[] camaras = new Camara[4];
    private Luz[] luces = new Luz[12];
    private Sensor[] sensores = new Sensor[4];
    private Televisor proyector = null;
    
    public Ambiente(Admin encargado) {
        adminEncargado = encargado;
        acondicionado[0] = new ACondicionado("Watercool 2", "LG", 30.0);
        acondicionado[1] = new ACondicionado("Watercool 2", "LG", 0.0);
        for(int i = 0 ; i < 12 ; i++) {
            luces[i] = new Luz("Wattmax 200", "OSRAM");
        }
        
        for(int i = 0 ; i < 4 ; i++) {
            sensores[i] = new Sensor("Wattmax 200", "OSRAM");
        }
        
        proyector = new Televisor("Samsung", "Projector");
        startAmbienteThread();
    }

    public void cargarPerfil() {
        //Conectar con = new Conectar();
        //con.consultarbd("SELECT * FROM ...");
    }
    
    public ACondicionado getACondicionado(int index) {
        return acondicionado[index];
    }
    
    public Camara getCamara(int index) {
        return camaras[index];
    }
    
    public Luz getLuz(int index) {
        return luces[index];
    }
    
    public Sensor getSensor(int index) {
        return sensores[index];
    }
    
    public Televisor getTelevisor() {
        return proyector;
    }
    
    public double getTemperaturaSala() {
        return temperaturaSala;
    }
    
    public Thread getAmbienteThread() {
        return ambienteThread;
    }
    
    public void startAmbienteThread() {
        System.out.println("I've been caleld!");
        ambienteThread = new Thread(new Runnable() {
            double increment = 0.0;
            double increment2 = 0.0;
            
            @Override
            public void run() {
                System.out.println("ContinueThread es " + ((continueThread) ? "true" : "false"));
                while(continueThread) {
                    increment = acondicionado[0].getTemperatura();
                    increment2 = acondicionado[1].getTemperatura();
                    System.out.println("Temp 1: " + increment + " | Temp 2: " + increment2);
                    try {
                        Thread.sleep(2500);
                        if(Math.round(Math.random()) == 1) {
                            increment += 0.02;
                            increment2 += 0.02;
                        }
                        else {
                            increment -= 0.02;
                            increment2 -= 0.02;
                        }
                        acondicionado[0].changeTemperatura(increment);
                        acondicionado[1].changeTemperatura(increment2);
                        temperaturaSala = (increment + increment2) / 2;
                    } 
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        ambienteThread.start();
    }
    
    public void toggleAmbienteThread() {
        continueThread = !continueThread;
    }
    
}