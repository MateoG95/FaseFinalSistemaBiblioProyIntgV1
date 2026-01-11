package sistemaBiblio.usuario;

public class Administrador extends Usuario {
    private String nivelAcceso;
    private String departamento;
    private boolean puedeTransferir;

    public Administrador(String cedula, String nombre, String telefono, String email,
                         String codigoUsuario, String nivelAcceso, String departamento) {
        super(cedula, nombre, telefono, email, codigoUsuario);

        if (nivelAcceso == null || nivelAcceso.trim().isEmpty()) {
            throw new IllegalArgumentException("Nivel de acceso no puede ser nulo o vacio");
        }
        if (departamento == null || departamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Departamento no puede ser nulo o vacio");
        }

        this.nivelAcceso = nivelAcceso;
        this.departamento = departamento;
        this.puedeTransferir = true; // Los administradores siempre pueden transferir
        this.maxLibrosPrestados = 10; // Administradores tienen mayor limite
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }

    // Getters especificos
    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public boolean puedeRealizarTransferencia() {
        return puedeTransferir;
    }

    // Metodo para validar si puede realizar operaciones administrativas
    public boolean puedeGestionarUsuarios() {
        return nivelAcceso.equals("ALTO") || nivelAcceso.equals("MEDIO");
    }

    public boolean puedeGestionarLibros() {
        return true; // Todos los administradores pueden gestionar libros
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nNivel de acceso: " + nivelAcceso +
                "\nDepartamento: " + departamento +
                "\nPuede transferir libros: " + (puedeTransferir ? "Si" : "No") +
                "\nPuede gestionar usuarios: " + (puedeGestionarUsuarios() ? "Si" : "No");
    }
}