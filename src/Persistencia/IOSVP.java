package persistencia;

import controlador.*;
import excepciones.*;
import modelo.*;
import utilidades.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.*;

public class IOSVP {

    private static IOSVP instancia;

    private IOSVP() {}

    public static IOSVP getInstance() {
        if (instancia == null) {
            instancia = new IOSVP();
        }
        return instancia;
    }

    public void saveControladores(Object[] controladores) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("SVPObjetos.obj")
            );
            oos.writeObject(controladores);
            oos.close();
        } catch (FileNotFoundException e) {
            throw new SVPException("No se puede abrir o crear el archivo SVPObjetos.obj");
        } catch (IOException e) {
            throw new SVPException("No se puede grabar en el archivo SVPObjetos.obj");
        }
    }

    public Object[] readControladores() {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream("SVPObjetos.obj")
            );
            Object[] controladores = (Object[]) ois.readObject();
            ois.close();
            return controladores;
        } catch (FileNotFoundException e) {
            throw new SVPException("No existe o no se puede abrir el archivo SVPObjetos.obj");
        } catch (IOException | ClassNotFoundException e) {
            throw new SVPException("No se puede leer el archivo SVPObjetos.obj");
        }
    }

    public void savePasajesDeVenta(Pasaje[] pasajes, String nombreArchivo) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
            for (int i = 0; i < pasajes.length; i++) {
                pw.print(pasajes[i].toString());
                if (i < pasajes.length - 1) {
                    pw.println();
                }
            }
            pw.close();
        } catch (IOException e) {
            throw new SVPException("No se puede abrir o crear el archivo " + nombreArchivo);
        }
    }

    public Object[] readDatosIniciales() {
        List<Object> objetos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("SVPDatosIniciales.txt"))) {
            String linea;
            int seccion = 0;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.equals("+")) {
                    seccion++;
                    continue;
                }
                if (linea.isEmpty()) continue;

                switch (seccion) {
                    case 0: procesarPersona(linea, objetos); break;
                    case 1: procesarEmpresa(linea, objetos); break;
                    case 2: procesarTripulante(linea, objetos); break;
                    case 3: procesarTerminal(linea, objetos); break;
                    case 4: procesarBus(linea, objetos); break;
                    case 5: procesarViaje(linea, objetos); break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new SVPException("No existe o no se puede abrir el archivo SVPDatosIniciales.txt");
        } catch (IOException e) {
            throw new SVPException("No existe o no se puede abrir el archivo SVPDatosIniciales.txt");
        }

        return objetos.toArray();
    }

    private void procesarPersona(String linea, List<Object> objetos) {
        String[] partes = linea.split(";");
        String tipo = partes[0];
        IdPersona id = Rut.of(partes[1]);
        Tratamiento trat = partes[2].equals("SR") ? Tratamiento.SR : Tratamiento.SRA;
        Nombre nombre = new Nombre(trat, partes[3], partes[4], partes[5]);
        String telefono = partes[6];

        if (tipo.equals("C")) {
            String email = partes[7];
            Cliente c = new Cliente(id, nombre, email);
            c.setTelefono(telefono);
            objetos.add(c);

        } else if (tipo.equals("P")) {
            Tratamiento tratContacto = partes[7].equals("SR") ? Tratamiento.SR : Tratamiento.SRA;
            Nombre nomContacto = new Nombre(tratContacto, partes[8], partes[9], partes[10]);
            String fonoContacto = partes[11];
            Pasajero p = new Pasajero(id, nombre);
            p.setTelefono(telefono);
            p.setNomContacto(nomContacto);
            p.setFonoContacto(fonoContacto);
            objetos.add(p);

        } else if (tipo.equals("CP")) {
            String email = partes[7];
            Tratamiento tratContacto = partes[8].equals("SR") ? Tratamiento.SR : Tratamiento.SRA;
            Nombre nomContacto = new Nombre(tratContacto, partes[9], partes[10], partes[11]);
            String fonoContacto = partes[12];

            Cliente c = new Cliente(id, nombre, email);
            c.setTelefono(telefono);
            objetos.add(c);

            Pasajero p = new Pasajero(id, nombre);
            p.setTelefono(telefono);
            p.setNomContacto(nomContacto);
            p.setFonoContacto(fonoContacto);
            objetos.add(p);
        }
    }

    private void procesarEmpresa(String linea, List<Object> objetos) {
        String[] partes = linea.split(";");
        Rut rut = Rut.of(partes[0]);
        String nombre = partes[1];
        String url = partes[2];

        Empresa empresa = new Empresa(rut, nombre);
        empresa.setUrl(url);
        objetos.add(empresa);
    }

    private void procesarTripulante(String linea, List<Object> objetos) {
        String[] partes = linea.split(";");
        String tipo = partes[0];
        IdPersona id = Rut.of(partes[1]);
        Tratamiento trat = partes[2].equals("SR") ? Tratamiento.SR : Tratamiento.SRA;
        Nombre nombre = new Nombre(trat, partes[3], partes[4], partes[5]);
        Direccion dir = new Direccion(partes[6], Integer.parseInt(partes[7]), partes[8]);

        if (tipo.equals("A")) {
            objetos.add(new Auxiliar(id, nombre, dir));
        } else if (tipo.equals("C")) {
            objetos.add(new Conductor(id, nombre, dir));
        }
    }

    private void procesarTerminal(String linea, List<Object> objetos) {
        String[] partes = linea.split(";");
        String nombre = partes[0];
        Direccion dir = new Direccion(partes[1], Integer.parseInt(partes[2]), partes[3]);

        Terminal terminal = new Terminal(nombre, dir);
        objetos.add(terminal);
    }

    private void procesarBus(String linea, List<Object> objetos) {
        String[] partes = linea.split(";");
        String patente = partes[0];
        String marca = partes[1];
        String modelo = partes[2];
        int nroAsientos = Integer.parseInt(partes[3]);
        Rut rutEmpresa = Rut.of(partes[4]);

        Empresa empresaAsociada = null;
        for (Object obj : objetos) {
            if (obj instanceof Empresa && ((Empresa) obj).getRut().equals(rutEmpresa)) {
                empresaAsociada = (Empresa) obj;
                break;
            }
        }

        Bus bus = new Bus(patente, marca, modelo, nroAsientos, empresaAsociada);
        objetos.add(bus);
    }

    private void procesarViaje(String linea, List<Object> objetos) {
        String[] partes = linea.split(";");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(partes[0], dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hora = LocalTime.parse(partes[1], timeFormatter);

        int precio = Integer.parseInt(partes[2]);
        int duracion = Integer.parseInt(partes[3]);

        String patente = partes[4];
        Rut rutAux = Rut.of(partes[5]);
        Rut rutCond = Rut.of(partes[6]);
        String nomTSalida = partes[7];
        String nomTLlegada = partes[8];

        Bus bus = null;
        Auxiliar aux = null;
        Conductor cond = null;
        Terminal tSalida = null;
        Terminal tLlegada = null;

        for (Object obj : objetos) {
            if (obj instanceof Bus && ((Bus) obj).getPatente().equalsIgnoreCase(patente)) {
                bus = (Bus) obj;
            } else if (obj instanceof Auxiliar && ((Auxiliar) obj).getIdPersona().equals(rutAux)) {
                aux = (Auxiliar) obj;
            } else if (obj instanceof Conductor && ((Conductor) obj).getIdPersona().equals(rutCond)) {
                cond = (Conductor) obj;
            } else if (obj instanceof Terminal) {
                Terminal t = (Terminal) obj;
                if (t.getNombre().equalsIgnoreCase(nomTSalida)) tSalida = t;
                if (t.getNombre().equalsIgnoreCase(nomTLlegada)) tLlegada = t;
            }
        }

        Viaje viaje = new Viaje(fecha, hora, precio, duracion, bus, aux, new Conductor[]{cond}, tSalida, tLlegada);
        objetos.add(viaje);
    }
    private Optional<Empresa> findEmpresa(List<Empresa> empresas, Rut rut) {
        return empresas.stream()
                .filter(e -> e.getRut().equals(rut))
                .findFirst();
    }

    private Optional<Bus> findBus(List<Bus> buses, String patente) {
        return buses.stream()
                .filter(b -> b.getPatente().equalsIgnoreCase(patente))
                .findFirst();
    }

    private Optional<Terminal> findTerminal(List<Terminal> terminales, String nombre) {
        return terminales.stream()
                .filter(t -> t.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    private Optional<Tripulante> findTripulante(Empresa empresa, IdPersona id, String rol) {
        return Arrays.stream(empresa.getTripulantes())
                .filter(t -> t.getIdPersona().equals(id))
                .filter(t -> (rol.equals("Auxiliar") && t instanceof Auxiliar) ||
                        (rol.equals("Conductor") && t instanceof Conductor))
                .findFirst();
    }
}