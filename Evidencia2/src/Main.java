import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Doctor {
    private int id;
    private String nombreCompleto;
    private String especialidad;

    public Doctor(int id, String nombreCompleto, String especialidad) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}

class Paciente {
    private int id;
    private String nombreCompleto;

    public Paciente(int id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}

class Cita {
    private int id;
    private String fechaHora;
    private String motivo;
    private Doctor doctor;
    private Paciente paciente;

    public Cita(int id, String fechaHora, String motivo, Doctor doctor, Paciente paciente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }
}

class SistemaClinico {
    private Map<Integer, Doctor> doctores = new HashMap<>();
    private Map<Integer, Paciente> pacientes = new HashMap<>();
    private List<Cita> citas = new ArrayList<>();
    private Map<String, String> credencialesAdmin = new HashMap<>();

    public void agregarDoctor(int id, String nombreCompleto, String especialidad) {
        Doctor doctor = new Doctor(id, nombreCompleto, especialidad);
        doctores.put(id, doctor);
    }

    public void agregarPaciente(int id, String nombreCompleto) {
        Paciente paciente = new Paciente(id, nombreCompleto);
        pacientes.put(id, paciente);
    }

    public void crearCita(int id, String fechaHora, String motivo, int idDoctor, int idPaciente) {
        Doctor doctor = doctores.get(idDoctor);
        Paciente paciente = pacientes.get(idPaciente);
        if (doctor != null && paciente != null) {
            Cita cita = new Cita(id, fechaHora, motivo, doctor, paciente);
            citas.add(cita);
        }
    }

    public void agregarCredencialesAdmin(String usuario, String contraseña) {
        credencialesAdmin.put(usuario, contraseña);
    }

    public boolean isAdminAutenticado(String usuario, String contraseña) {
        String contraseñaAlmacenada = credencialesAdmin.get(usuario);
        return contraseñaAlmacenada != null && contraseñaAlmacenada.equals(contraseña);
    }

    public void listarDoctores() {
        for (Doctor doctor : doctores.values()) {
            System.out.println("ID del Doctor: " + doctor.getId());
            System.out.println("Nombre Completo: " + doctor.getNombreCompleto());
            System.out.println("Especialidad: " + doctor.getEspecialidad());
            System.out.println();
        }
    }

    public void listarPacientes() {
        for (Paciente paciente : pacientes.values()) {
            System.out.println("ID del Paciente: " + paciente.getId());
            System.out.println("Nombre Completo: " + paciente.getNombreCompleto());
            System.out.println();
        }
    }

    public void listarCitas() {
        for (Cita cita : citas) {
            System.out.println("ID de la Cita: " + cita.getId());
            System.out.println("Fecha y Hora: " + cita.getFechaHora());
            System.out.println("Motivo: " + cita.getMotivo());
            System.out.println("Doctor: " + cita.getDoctor().getNombreCompleto());
            System.out.println("Paciente: " + cita.getPaciente().getNombreCompleto());
            System.out.println();
        }
    }

    public void guardarDatos(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // Guardar datos de doctores
            for (Doctor doctor : doctores.values()) {
                writer.write("Doctor," + doctor.getId() + "," + doctor.getNombreCompleto() + "," + doctor.getEspecialidad());
                writer.newLine();
            }

            // Guardar datos de pacientes
            for (Paciente paciente : pacientes.values()) {
                writer.write("Paciente," + paciente.getId() + "," + paciente.getNombreCompleto());
                writer.newLine();
            }

            // Guardar datos de citas
            for (Cita cita : citas) {
                writer.write("Cita," + cita.getId() + "," + cita.getFechaHora() + "," + cita.getMotivo() + "," + cita.getDoctor().getId() + "," + cita.getPaciente().getId());
                writer.newLine();
            }

            // Guardar credenciales de administrador
            for (Map.Entry<String, String> entry : credencialesAdmin.entrySet()) {
                writer.write("Admin," + entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length > 1) {
                    String tipo = partes[0];
                    if ("Doctor".equals(tipo) && partes.length == 4) {
                        int id = Integer.parseInt(partes[1]);
                        String nombreCompleto = partes[2];
                        String especialidad = partes[3];
                        agregarDoctor(id, nombreCompleto, especialidad);
                    } else if ("Paciente".equals(tipo) && partes.length == 3) {
                        int id = Integer.parseInt(partes[1]);
                        String nombreCompleto = partes[2];
                        agregarPaciente(id, nombreCompleto);
                    } else if ("Cita".equals(tipo) && partes.length == 6) {
                        int id = Integer.parseInt(partes[1]);
                        String fechaHora = partes[2];
                        String motivo = partes[3];
                        int idDoctor = Integer.parseInt(partes[4]);
                        int idPaciente = Integer.parseInt(partes[5]);
                        crearCita(id, fechaHora, motivo, idDoctor, idPaciente);
                    } else if ("Admin".equals(tipo) && partes.length == 3) {
                        String usuario = partes[1];
                        String contraseña = partes[2];
                        agregarCredencialesAdmin(usuario, contraseña);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SistemaClinico sistemaClinico = new SistemaClinico();
        sistemaClinico.cargarDatos("datos.csv");

        sistemaClinico.agregarCredencialesAdmin("admin", "contraseña");
        Scanner scanner = new Scanner(System.in);

        boolean autenticado = false;
        while (!autenticado) {
            System.out.print("Ingrese su nombre de usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contraseña = scanner.nextLine();

            if (sistemaClinico.isAdminAutenticado(usuario, contraseña)) {
                autenticado = true;
                System.out.println("Inicio de sesión exitoso.");
            } else {
                System.out.println("Nombre de usuario o contraseña incorrectos. Intente nuevamente.");
            }
        }

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Agregar Doctor");
            System.out.println("2. Agregar Paciente");
            System.out.println("3. Crear Cita");
            System.out.println("4. Lista de Doctores");
            System.out.println("5. Lista de Pacientes");
            System.out.println("6. Lista de Citas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el carácter de nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID del Doctor: ");
                    int idDoctor = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese Nombre Completo del Doctor: ");
                    String nombreDoctor = scanner.nextLine();
                    System.out.print("Ingrese Especialidad del Doctor: ");
                    String especialidadDoctor = scanner.nextLine();
                    sistemaClinico.agregarDoctor(idDoctor, nombreDoctor, especialidadDoctor);
                    break;
                case 2:
                    System.out.print("Ingrese ID del Paciente: ");
                    int idPaciente = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese Nombre Completo del Paciente: ");
                    String nombrePaciente = scanner.nextLine();
                    sistemaClinico.agregarPaciente(idPaciente, nombrePaciente);
                    break;
                case 3:
                    System.out.print("Ingrese ID de la Cita: ");
                    int idCita = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese Fecha y Hora (por ejemplo, yyyy-MM-dd HH:mm): ");
                    String fechaHora = scanner.nextLine();
                    System.out.print("Ingrese Motivo de la Cita: ");
                    String motivoCita = scanner.nextLine();
                    System.out.print("Ingrese ID del Doctor: ");
                    int idDoctorCita = scanner.nextInt();
                    System.out.print("Ingrese ID del Paciente: ");
                    int idPacienteCita = scanner.nextInt();
                    sistemaClinico.crearCita(idCita, fechaHora, motivoCita, idDoctorCita, idPacienteCita);
                    break;
                case 4:
                    sistemaClinico.listarDoctores();
                    break;
                case 5:
                    sistemaClinico.listarPacientes();
                    break;
                case 6:
                    sistemaClinico.listarCitas();
                    break;
                case 7:
                    scanner.close();
                    System.out.println("Cerrando el programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción invalida. Intente de nuevo.");
                    break;
            }
            sistemaClinico.guardarDatos("datos.csv");
        }
    }
}


