public class Alumno {
    private String nombreEstudiante;
    private double[] calificaciones = new double[5];

    public Alumno(String nombre, double[] calificaciones) {
        this.nombreEstudiante = nombre;
        this.calificaciones = calificaciones;
    }

    // Calcular promedio
    public double calcularPromedio() {
        double suma = 0;
        for (double calificacion : calificaciones) {
            suma += calificacion;
        }
        return suma / calificaciones.length;
    }

    // Condicionales de calificaciones
    public char ResultadoCalificacion(double promedio) {
        if (promedio >= 91 && promedio <= 100) {
            return 'A';
        } else if (promedio >= 51 && promedio <= 60) {
            return 'E';
        } else if (promedio >= 61 && promedio <= 70) {
            return 'D';
        } else if (promedio >= 71 && promedio <= 80) {
            return 'C';
        } else if (promedio >= 81 && promedio <= 90) {
            return 'B';
        } else {
            return 'F';
        }
    }

    // Impresion de resultados
    public void ResultadoObtenido() {
        System.out.println("Nombre del estudiante: " + nombreEstudiante);
        for (int i = 0; i < calificaciones.length; i++) {
            System.out.println("Calificacion " + (i + 1) + ": " + calificaciones[i]);
        }
        double promedio = calcularPromedio();
        char calificacion = ResultadoCalificacion(promedio);
        System.out.println("Promedio: " + promedio);
        System.out.println("Calificacion: " + calificacion);
    }

    //Ejecución de programa
    public static void main(String[] args) {
        String nombre = "Sergio Ordaz";
        double[] calificaciones = {56, 72, 97, 58, 86};
        Alumno alumno = new Alumno(nombre, calificaciones);
        alumno.ResultadoObtenido();
    }
}
