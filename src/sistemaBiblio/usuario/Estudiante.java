package sistemaBiblio.usuario;

public class Estudiante extends Usuario {
    private String carrera;
    private int semestre;

    public Estudiante(String cedula, String nombre, String telefono, String email,
                      String codigoUsuario, String carrera, int semestre) {
        super(cedula, nombre, telefono, email, codigoUsuario);

        if (carrera == null || carrera.trim().isEmpty()) {
            throw new IllegalArgumentException("Carrera no puede ser nula o vacía");
        }
        if (semestre < 1 || semestre > 10) {
            throw new IllegalArgumentException("Semestre debe estar entre 1 y 10");
        }

        this.carrera = carrera;
        this.semestre = semestre;
        this.maxLibrosPrestados = 3; // Límite para estudiantes
    }

    @Override
    public String getTipo() {
        return "Estudiante";
    }

    // Getters específicos
    public String getCarrera() {
        return carrera;
    }

    public int getSemestre() {
        return semestre;
    }

    // Métodos específicos de estudiante
    public boolean puedePrestamoEspecial() {
        return semestre >= 5; // Estudiantes de semestres avanzados pueden préstamos especiales
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nCarrera: " + carrera +
                "\nSemestre: " + semestre +
                "\nPréstamos especiales: " + (puedePrestamoEspecial() ? "Disponible" : "No disponible");
    }
}