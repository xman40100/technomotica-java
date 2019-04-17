package techdomotica.objs;

import techdomotica.objs.comps.Televisor;
import techdomotica.objs.comps.Sensor;
import techdomotica.objs.comps.Luz;
import techdomotica.objs.comps.Camara;
import techdomotica.objs.comps.ACondicionado;

public class Ambiente {
    
    private Conectar connection;
    
    private Thread ambienteThread;
    private Thread personaThread;
    private Thread deviceThread;
    
    private Time runTime;
    
    private boolean continueAmbienteThread = true;
    private boolean continuePersonaThread = true;
    private boolean continueDeviceThread = true;
    
    private String perfilActual = "";
    private Admin adminEncargado = null;
    
    private int personasEnAmbiente = 0;//Cada persona debería de generar una temperatura de 1°C
    private int personasDetectadas = 0;
    private int personasDetectadasP = 0;
    
    private double temperaturaSala = 0.0,//La temperatura de la sala se modifica dentro de este archivo.
            temperaturaAmbiente = 0.0,//La temperatura ambiente se modifica dentro del Main, debido a que no hay acceso al hilo de tiempo.
            temperaturaPersonas = 0.0;

    private ACondicionado[] acondicionado = new ACondicionado[2];
    private Camara[] camaras = new Camara[4];
    private Luz[] luces = new Luz[12];
    private Sensor[] sensores = new Sensor[2];
    private Televisor proyector = null;
    
    private Config config;
    
    public Ambiente(Admin encargado) {
        config = new Config();
        connection = new Conectar();
        loadComponentes();
        
        adminEncargado = encargado;
        
        
        startTimeThread();
        startAmbienteThread();
        startPersonaThread();
        startDeviceThread();
    }

    public void loadComponentes() {
        loadACondicionados();
        loadCamaras();
        loadLuces();
        loadSensores();
        loadProyector();
        loadPerfil();
    }
    
    public void createACondicionado(int index, String model, String mark) {
        acondicionado[index] = new ACondicionado(model, mark);
        acondicionado[index].toggleComponenteEncendido(true);
    }
    
    public void createACondicionado(int index, String model, String mark, double value) {
        acondicionado[index] = new ACondicionado(model, mark, value);
        acondicionado[index].toggleComponenteEncendido(true);
    }
    
    public void loadACondicionados() {
        connection.executeRS("SELECT * FROM acondicionado INNER JOIN componente ON acondicionado.id_componente = componente.id_componente WHERE 1 LIMIT 2;");
        int i = 0;
        while(connection.nextRow()) {
            createACondicionado(i, String.valueOf(connection.getResultSetRow("nom_componente")), String.valueOf(connection.getResultSetRow("marca")), Double.parseDouble(String.valueOf(connection.getResultSetRow("uso"))));
            acondicionado[i].setGastoEnergetico(Double.parseDouble(String.valueOf(connection.getResultSetRow("gasto_energetico"))));
            acondicionado[i].changeTemperatura(Double.parseDouble(String.valueOf(connection.getResultSetRow("temperatura"))));
            System.out.println(acondicionado[i].getTemperatura());
            if(Integer.parseInt(String.valueOf(connection.getResultSetRow("componente_on"))) == 1) acondicionado[i].toggleComponenteEncendido(true);
            else acondicionado[i].toggleComponenteEncendido(false);
            i++;
        }
        
        //createACondicionado(0, "9000btu", "LG", 45.0);
    }

    public void loadLuces() {
        for(int i = 0 ; i < 12 ; i++) {
            luces[i] = new Luz("Wattmax 200", "OSRAM");
        }
    }
    
    public void createSensor(int index, String model, String mark) {
        sensores[index] = new Sensor(model, mark);
        sensores[index].toggleComponenteEncendido(true);
    }
    
    public void createSensor(int index, String model, String mark, double value) {
        sensores[index] = new Sensor(model, mark, value);
        sensores[index].toggleComponenteEncendido(true);
    }
    
    public void loadSensores() {
        connection.executeRS("SELECT * FROM sensor INNER JOIN componente ON sensor.id_componente = componente.id_componente WHERE 1 LIMIT 2;");
        int i = 0;
        while(connection.nextRow()) {
            if(String.valueOf(connection.getResultSetRow("tiposensor")).equalsIgnoreCase("puerta")) i = 0;
            else i = 1;
            createSensor(i, String.valueOf(connection.getResultSetRow("nom_componente")), String.valueOf(connection.getResultSetRow("marca")), Double.parseDouble(String.valueOf(connection.getResultSetRow("uso"))));
            sensores[i].setGastoEnergetico(Double.parseDouble(String.valueOf(connection.getResultSetRow("gasto_energetico"))));
            sensores[i].setTipoSensor(String.valueOf(connection.getResultSetRow("tiposensor")));
            if(Integer.parseInt(String.valueOf(connection.getResultSetRow("componente_on"))) == 1) sensores[i].toggleComponenteEncendido(true);
            else sensores[i].toggleComponenteEncendido(false);
        }
    }
    
    public void loadProyector() {
        connection.executeRSOne("SELECT * FROM tv INNER JOIN componente ON tv.id_componente = componente.id_componente WHERE 1 LIMIT 1;");
        
        proyector = new Televisor(String.valueOf(connection.getResultSetRow("nom_componente")), String.valueOf(connection.getResultSetRow("marca")), Double.parseDouble(String.valueOf(connection.getResultSetRow("uso"))));
        proyector.setCalidadTV(String.valueOf(connection.getResultSetRow("calidadtv")));
        proyector.setResolucion(String.valueOf(connection.getResultSetRow("resolucion")));
    }
    
    public void createCamara(int index, String model, String mark) {
        camaras[index] = new Camara(model, mark);
        camaras[index].toggleComponenteEncendido(true);
    }
    
    public void createCamara(int index, String model, String mark, double value) {
        camaras[index] = new Camara(model, mark, value);
        camaras[index].toggleComponenteEncendido(true);
    }
    
    public void createTelevisor(String model, String mark) {
        proyector = new Televisor(model, mark);
    }
    
    public void createTelevisor(String model, String mark, double value) {
        proyector = new Televisor(model, mark, value);
    }
    
    public void loadCamaras() {
        connection.executeRS("SELECT * FROM camara INNER JOIN componente ON camara.id_componente = componente.id_componente WHERE 1 LIMIT 4;");
        int i = 0;
        while(connection.nextRow()) {
            i = Integer.parseInt(String.valueOf(connection.getResultSetRow("ubicacion"))) - 1;
            createCamara(i, String.valueOf(connection.getResultSetRow("nom_componente")), String.valueOf(connection.getResultSetRow("marca")), Double.parseDouble(String.valueOf(connection.getResultSetRow("uso"))));
            camaras[i].setGastoEnergetico(Double.parseDouble(String.valueOf(connection.getResultSetRow("gasto_energetico"))));
            camaras[i].setResolucion(String.valueOf(connection.getResultSetRow("resolucion")));
            if(Integer.parseInt(String.valueOf(connection.getResultSetRow("componente_on"))) == 1) camaras[i].toggleComponenteEncendido(true);
            else camaras[i].toggleComponenteEncendido(false);
        }
    }
    
    public void loadPerfil() {
        System.out.println("Not implemented!");
        //Conectar con = new Conectar();
        //con.consultarbd("SELECT * FROM ...");
    }
    
    public ACondicionado getACondicionado(int index) {
        return (acondicionado[index] != null) ? acondicionado[index] : null;
    }
    
    public ACondicionado[] getACondicionadoAsArray() {
        return acondicionado;
    }
    
    public void destroyACondicionado(int index) {
        acondicionado[index] = null;
    }
    
    public Camara getCamara(int index) {
        return (camaras[index] != null) ? camaras[index] : null;
    }
    
    public Camara[] getCamaraAsArray() {
        return camaras;
    }
    
    public void destroyCamara(int index) {
        camaras[index] = null;
    }
    
    public Luz getLuz(int index) {
        return luces[index];
    }
    
    public Sensor getSensor(int index) {
        return (sensores[index] != null) ? sensores[index] : null;
    }
    
    public Sensor[] getSensorAsArray() {
        return sensores;
    }
    
    public void destroySensor(int index) {
        sensores[index] = null;
    }
    
    public Televisor getTelevisor() {
        return (proyector != null) ? proyector : null;
    }
    
    public void destroyTelevisor() {
        proyector = null;
    }
    
    public double getTemperaturaSala() {
        return temperaturaSala;
    }
    
    public double getTemperaturaAmbiente() {
        return temperaturaAmbiente;
    }
    
    public void setTemperaturaAmbiente(double temp) {
        temperaturaAmbiente = temp;
    }
    
    public int getPersonasInAmbiente() {
        return personasEnAmbiente;
    }
    
    public int getPersonasDetectadas() {
        return personasDetectadas;
    }
    
    public int getPersonasDetectadasP() {
        return personasDetectadasP;
    }
    
    public Thread getPersonaThread() {
        return personaThread;
    }
    
    public Thread getAmbienteThread() {
        return ambienteThread;
    }
    
    public Thread getDeviceThread() {
        return deviceThread;
    }
    
    public Time getTimeThread() {
        return runTime;
    }
    
    public void startTimeThread() {
        runTime = new Time();
        runTime.start();
    }
    
    public void startPersonaThread() {
        personaThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(continuePersonaThread) {
                    try {//temperaturaPersonas = personasEnAmbiente * 0.5;
                        Thread.sleep(1000);
                        if(runTime.getHours() == 7 && runTime.getMinutes() == 0 && runTime.getSeconds() == 0) {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        for(int i = 1 ; i <= 21 ; i++) {
                                            Thread.sleep(2000);
                                            personasEnAmbiente++;
                                            temperaturaAmbiente++;
                                            personasDetectadasP++;
                                            if(sensores[0] != null && sensores[0].getComponenteEncendidoState()) personasDetectadas++;
                                        }
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            t.start();
                        }
                        else if(runTime.getHours() == 9 && runTime.getMinutes() == 30 && runTime.getSeconds() == 0) {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        for(int i = 1 ; i <= 21 ; i++) {
                                            Thread.sleep(2000);
                                            personasEnAmbiente--;
                                            temperaturaAmbiente--;
                                            personasDetectadasP++;
                                        }
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            t.start();
                        }
                        else if(runTime.getHours() == 10 && runTime.getMinutes() == 0 && runTime.getSeconds() == 0) {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        for(int i = 1 ; i <= 21 ; i++) {
                                            Thread.sleep(2000);
                                            personasEnAmbiente++;
                                            temperaturaAmbiente++;
                                        }
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            t.start();
                        }
                        else if(runTime.getHours() == 12 && runTime.getMinutes() == 0 && runTime.getSeconds() == 0) {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        for(int i = 1 ; i <= 21 ; i++) {
                                            Thread.sleep(2000);
                                            personasEnAmbiente--;
                                            temperaturaAmbiente--;
                                            personasDetectadasP++;
                                        }
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            t.start();
                        }
                        else if(runTime.getHours() == 15 && runTime.getMinutes() == 0 && runTime.getSeconds() == 0) {
                            personasEnAmbiente = 1;
                            personasDetectadasP++;
                        }
                        else if(runTime.getHours() == 15 && runTime.getMinutes() == 10 && runTime.getSeconds() == 0) {
                            personasEnAmbiente = 0;
                            personasDetectadasP++;
                        }
                        else if(runTime.getHours() == 23 && runTime.getMinutes() == 59 && runTime.getSeconds() == 59) {
                            if(sensores[0] != null && sensores[0].getComponenteEncendidoState()) {
                                personasDetectadas = 0;
                                personasDetectadasP = 0;
                            }
                        }
                    }
                    catch(InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        personaThread.start();
    }
    
    public Conectar getConnection() {
        return connection;
    }
    
    public void startAmbienteThread() {
        ambienteThread = new Thread(new Runnable(){
            @Override
            public void run() {
                double increment1 = 0.0, increment2 = 0.0;
                while(continueAmbienteThread) {
                    try {
                        Thread.sleep(2500);
                        System.out.println("I've been called!");
                        if(acondicionado[0] != null && acondicionado[0].getComponenteEncendidoState()) {
                            increment1 = acondicionado[0].getTemperatura();
                            if(Math.round(Math.random()) == 1) increment1 += 0.06;
                            else increment1 -= 0.06;
                            acondicionado[0].changeTemperatura(increment1);
                        }
                        if(acondicionado[1] != null && acondicionado[1].getComponenteEncendidoState()) {
                            increment2 = acondicionado[1].getTemperatura();
                            if(Math.round(Math.random()) == 1) increment2 += 0.06;
                            else increment2 -= 0.06;
                            acondicionado[1].changeTemperatura(increment2);
                        }
                        if(increment1 != 0.0 && increment2 != 0.0) temperaturaSala = (increment1 + increment2) / 2;
                        else if(increment1 == 0.0 && increment2 != 0.0) temperaturaSala = increment2;
                        else temperaturaSala = temperaturaAmbiente;
                    }
                    catch(InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        });
        ambienteThread.start();
    }
    
//    //TODO: Fix this damn logic below lmao.
//    public void startAmbienteThread() {
//        //System.out.println("I've been caleld!");
//        ambienteThread = new Thread(new Runnable() {
//            double increment = 0.0;
//            double increment2 = 0.0;
//            
//            @Override
//            public void run() {
//                //System.out.println("ContinueThread es " + ((continueAmbienteThread) ? "true" : "false"));
//                //Temperatura de la sala:
//                if((acondicionado[0] != null && acondicionado[0].getComponenteEncendidoState()) && (acondicionado[1] != null && !acondicionado[1].getComponenteEncendidoState())) {
//                    System.out.println("Hecck yeah 1");
//                    while(continueAmbienteThread) {
//                        increment = acondicionado[0].getTemperatura();
//                        increment2 = acondicionado[1].getTemperatura();
//                        System.out.println("Temp 1: " + increment + " | Temp 2: " + increment2);
//                        try {
//                            Thread.sleep(2500);
//                            if(Math.round(Math.random()) == 1) {
//                                if(temperaturaAmbiente > temperaturaSala) {
//                                    increment += 0.06;
//                                    increment2 += 0.06;
//                                }
//                                else {
//                                    increment += 0.02;
//                                    increment2 += 0.02;
//                                }
//                            }
//                            else {
//                                if(temperaturaAmbiente > temperaturaSala) {
//                                    increment -= 0.06;
//                                    increment2 -= 0.06;
//                                }
//                                else {
//                                    increment -= 0.02;
//                                    increment2 -= 0.02;
//                                }
//                            }
//                            acondicionado[0].changeTemperatura(increment);
//                            acondicionado[1].changeTemperatura(increment2);
//                            temperaturaSala = (increment + increment2) / 2;
//                        } 
//                        catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//                else if((acondicionado[0] != null && acondicionado[0].getComponenteEncendidoState()) && (acondicionado[1] == null || !acondicionado[1].getComponenteEncendidoState())) {
//                    System.out.println("Hecck yeah 2");
//                    while(continueAmbienteThread) {
//                        increment = acondicionado[0].getTemperatura();
//                        System.out.println("Temp 1: " + increment);
//                        try {
//                            Thread.sleep(2500);
//                            if(Math.round(Math.random()) == 1) {
//                                if(temperaturaAmbiente > temperaturaSala) increment += 0.02;
//                                else increment += 0.06;
//                            }
//                            else {
//                                if(temperaturaAmbiente > temperaturaSala) increment -= 0.06;
//                                else increment -= 0.02;
//                            }
//                            
//                            acondicionado[0].changeTemperatura(increment);
//                            temperaturaSala = increment;
//                        } 
//                        catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//                else if((acondicionado[0] == null || acondicionado[0].getComponenteEncendidoState()) && (acondicionado[1] != null && !acondicionado[1].getComponenteEncendidoState())) {
//                    while(continueAmbienteThread) {
//                        System.out.println("Hecck yeah 3");
//                        increment = acondicionado[1].getTemperatura();
//                        System.out.println("Temp 1: " + increment);
//                        try {
//                            Thread.sleep(2500);
//                            if(Math.round(Math.random()) == 1) {
//                                if(temperaturaAmbiente > temperaturaSala) increment += 0.02;
//                                else increment += 0.06;
//                            }
//                            else {
//                                if(temperaturaAmbiente > temperaturaSala) increment -= 0.06;
//                                else increment -= 0.02;
//                            }
//                            
//                            acondicionado[1].changeTemperatura(increment);
//                            temperaturaSala = increment;
//                        } 
//                        catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//                else {
//                    System.out.println("Hecck yeah 4");
//                    temperaturaSala = 0;
//                }
//            }
//        });
//        ambienteThread.start();
//    }
    
    public void startDeviceThread() {
        deviceThread = new Thread(new Runnable(){
            @Override
            public void run() {
                int rate = Integer.parseInt(config.getConfigKey("deteriorationprogress"));
                if(rate == 0) rate = 1;
                while(continueDeviceThread) {
                    try {
                        Thread.sleep(600000 / rate);
                        System.out.println("How fast boi?");
                        for(ACondicionado ac : acondicionado) {
                            if(ac != null) {
                                if(ac.getComponenteEncendidoState()) ac.decrementarUsoComponente();
                            }
                        }
                        
                        for(Sensor sen : sensores) {
                            if(sen != null) {
                                if(sen.getComponenteEncendidoState()) sen.decrementarUsoComponente();
                            }
                        }
                        
                        for(Camara cam : camaras) {
                            if(cam != null) {
                                if(cam.getComponenteEncendidoState()) cam.decrementarUsoComponente();
                            }
                        }
                        if(proyector != null){
                            if(proyector.getComponenteEncendidoState()) proyector.decrementarUsoComponente();
                        }
                    }
                    catch(InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        });
        deviceThread.start();
    }
    
    public void toggleAmbienteThread() {
        continueAmbienteThread = !continueAmbienteThread;
        ambienteThread.interrupt();
    }
    
    public void toggleAmbienteThread(boolean togg) {
        continueAmbienteThread = togg;
        if(togg == false) ambienteThread.interrupt();
    }
    
    public void toggleTimeThread(boolean togg) {
        continuePersonaThread = togg;
        if(togg == false) {
            runTime.disableTimeThread();
            runTime.interrupt();
        }
    }
    
    public void toggleDeviceThread(boolean togg) {
        continueDeviceThread = togg;
        if(togg == false) {
            deviceThread.interrupt();
        }
    }
    
    public Config getConfig() {
        return config;
    }
}