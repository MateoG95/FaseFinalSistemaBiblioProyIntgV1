package sistemaBiblio.prestamos;

import sistemaBiblio.core.Libro;
import sistemaBiblio.multas.Multa;
import sistemaBiblio.usuario.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Prestamo {
    private String codigoPrestamo;
    private Usuario usuario;
    private Libro libro;
    private LocalDateTime fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaRealDevolucion;
    private boolean devuelto;
    private Multa multa;
    private String sucursal;

    public Prestamo(String codigoPrestamo, Usuario usuario, Libro libro,
                    LocalDate fechaDevolucion, String sucursal) {
        if (codigoPrestamo == null || codigoPrestamo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de préstamo no puede ser nulo o vacío");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        }
        if (libro == null) {
            throw new IllegalArgumentException("Libro no puede ser nulo");
        }
        if (fechaDevolucion == null) {
            throw new IllegalArgumentException("Fecha de devolución no puede ser nula");
        }

        this.codigoPrestamo = codigoPrestamo;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = LocalDateTime.now();
        this.fechaDevolucion = fechaDevolucion;
        this.fechaRealDevolucion = null;
        this.devuelto = false;
        this.multa = null;
        this.sucursal = sucursal;

        // Agregar préstamo al usuario
        usuario.agregarPrestamo(codigoPrestamo);
        // Marcar libro como no disponible
        libro.setDisponible(false);
    }

    public boolean devolver(LocalDate fechaDevolucionReal) {
        if (devuelto) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }

        this.devuelto = true;
        this.fechaRealDevolucion = fechaDevolucionReal;
        libro.setDisponible(true);
        usuario.finalizarPrestamo(codigoPrestamo);

        // Verificar si hay retraso
        if (fechaDevolucionReal.isAfter(fechaDevolucion)) {
            // Crear multa por retraso
            String codigoMulta = "MUL-" + codigoPrestamo;
            double monto = 5.0; // Multa fija de $5
            String motivo = "Retraso en devolución del libro: " + libro.getTitulo();
            this.multa = new Multa(codigoMulta, usuario, monto, motivo);

            // Agregar multa al usuario
            usuario.agregarMulta(codigoMulta, true);

            return false; // Hubo retraso
        }

        return true; // Devolución a tiempo
    }

    public boolean tieneMulta() {
        return multa != null;
    }

    public Multa getMulta() {
        return multa;
    }

    public String getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public String getSucursal() {
        return sucursal;
    }


    @Override
    public String toString() {
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fechaHoraFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigoPrestamo);
        sb.append("\nUsuario: ").append(usuario.getNombre());
        sb.append("\nLibro: ").append(libro.getTitulo());
        sb.append("\nFecha préstamo: ").append(fechaPrestamo.format(fechaHoraFormatter));
        sb.append("\nFecha devolución esperada: ").append(fechaDevolucion.format(fechaFormatter));

        if (devuelto) {
            sb.append("\nFecha devolución real: ").append(fechaRealDevolucion.format(fechaFormatter));
            sb.append("\nEstado: DEVUELTO");
        } else {
            sb.append("\nEstado: ACTIVO");
        }

        if (tieneMulta()) {
            sb.append("\nMULTA ACTIVA: $").append(multa.getMonto());
        }

        return sb.toString();
    }

}