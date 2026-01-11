package sistemaBiblio.usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Usuario {
    private String cedula;
    private String nombre;
    private String telefono;
    private String email;
    private String codigoUsuario;
    protected int maxLibrosPrestados;
    protected ArrayList<String> multasActivas;
    protected ArrayList<String> multasHistoricas;
    protected ArrayList<String> prestamosActivos;
    protected ArrayList<String> prestamosHistoricos;
    protected boolean puedePrestar;
    protected LocalDate fechaFinSancion;

    public Usuario(String cedula, String nombre, String telefono, String email,
                   String codigoUsuario) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("Cédula no puede ser nula o vacía");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede ser nulo o vacío");
        }
        if (codigoUsuario == null || codigoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de usuario no puede ser nulo o vacío");
        }

        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.codigoUsuario = codigoUsuario;
        this.multasActivas = new ArrayList<>();
        this.multasHistoricas = new ArrayList<>();
        this.prestamosActivos = new ArrayList<>();
        this.prestamosHistoricos = new ArrayList<>();
        this.puedePrestar = true;
        this.fechaFinSancion = null;
    }

    // Getters
    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public int getMaxLibrosPrestados() {
        return maxLibrosPrestados;
    }

    // Métodos comunes
    public boolean puedeRealizarPrestamo() {
        if (!puedePrestar) {
            if (fechaFinSancion != null && LocalDate.now().isAfter(fechaFinSancion)) {
                puedePrestar = true;
                fechaFinSancion = null;
                return true;
            }
            return false;
        }
        return true;
    }

    public void agregarMulta(String codigoMulta, boolean activa) {
        if (activa) {
            multasActivas.add(codigoMulta);
            puedePrestar = false;
            fechaFinSancion = LocalDate.now().plusMonths(1);
        } else {
            multasHistoricas.add(codigoMulta);
        }
    }

    public boolean pagarMulta(String codigoMulta) {
        if (multasActivas.remove(codigoMulta)) {
            multasHistoricas.add(codigoMulta);
            if (multasActivas.isEmpty()) {
                puedePrestar = true;
                fechaFinSancion = null;
            }
            return true;
        }
        return false;
    }

    public void agregarPrestamo(String codigoPrestamo) {
        prestamosActivos.add(codigoPrestamo);
    }

    public void finalizarPrestamo(String codigoPrestamo) {
        if (prestamosActivos.remove(codigoPrestamo)) {
            prestamosHistoricos.add(codigoPrestamo);
        }
    }

    public int getCantidadPrestamosActivos() {
        return prestamosActivos.size();
    }

    public int getTotalPrestamos() {
        return prestamosHistoricos.size() + prestamosActivos.size();
    }

    public int getTotalMultas() {
        return multasHistoricas.size() + multasActivas.size();
    }

    public boolean tieneMultasActivas() {
        return !multasActivas.isEmpty();
    }

    public ArrayList<String> getMultasActivas() {
        return new ArrayList<>(multasActivas);
    }

    // Método abstracto para tipo específico
    public abstract String getTipo();

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append("Cédula: ").append(cedula);
        sb.append("\nNombre: ").append(nombre);
        sb.append("\nTeléfono: ").append(telefono);
        sb.append("\nEmail: ").append(email);
        sb.append("\nCódigo Usuario: ").append(codigoUsuario);
        sb.append("\nTipo: ").append(getTipo());
        sb.append("\nLímite préstamos: ").append(maxLibrosPrestados);
        sb.append("\nPréstamos activos: ").append(prestamosActivos.size());
        sb.append("\nTotal préstamos: ").append(getTotalPrestamos());
        sb.append("\nMultas activas: ").append(multasActivas.size());
        sb.append("\nTotal multas: ").append(getTotalMultas());
        sb.append("\nPuede prestar: ").append(puedePrestar ? "Sí" : "No");

        if (fechaFinSancion != null) {
            sb.append("\nFin sanción: ").append(fechaFinSancion.format(formatter));
        }

        return sb.toString();
    }
}