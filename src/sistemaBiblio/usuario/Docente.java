package sistemaBiblio.usuario;

public class Docente extends Usuario {
    private String departamento;
    private String titulo;
    private boolean investigadorActivo;

    public Docente(String cedula, String nombre, String telefono, String email,
                   String codigoUsuario, String departamento, String titulo, boolean investigadorActivo) {
        super(cedula, nombre, telefono, email, codigoUsuario);

        if (departamento == null || departamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Departamento no puede ser nulo o vacío");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título no puede ser nulo o vacío");
        }

        this.departamento = departamento;
        this.titulo = titulo;
        this.investigadorActivo = investigadorActivo;
        this.maxLibrosPrestados = 5; // Límite para docentes
    }

    @Override
    public String getTipo() {
        return "Docente";
    }

    // Getters específicos
    public String getDepartamento() {
        return departamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isInvestigadorActivo() {
        return investigadorActivo;
    }

    // Métodos específicos de docente
    public boolean puedePrestamoInvestigacion() {
        return investigadorActivo;
    }

    public int getDiasPrestamoInvestigacion() {
        return investigadorActivo ? 60 : 30; // 60 días para investigación, 30 días normal
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nDepartamento: " + departamento +
                "\nTítulo: " + titulo +
                "\nInvestigador activo: " + (investigadorActivo ? "Sí" : "No") +
                "\nPréstamo investigación: " + (puedePrestamoInvestigacion() ?
                "Disponible (" + getDiasPrestamoInvestigacion() + " días)" : "No disponible");
    }
}