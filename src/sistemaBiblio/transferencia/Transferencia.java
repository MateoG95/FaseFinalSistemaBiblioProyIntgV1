package sistemaBiblio.transferencia;

import sistemaBiblio.core.Libro;
import sistemaBiblio.usuario.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transferencia {
    private String codigoTransferencia;
    private Libro libro;
    private String sucursalOrigen;
    private String sucursalDestino;
    private Usuario solicitante;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaCompletada;
    private String estado; // "SOLICITADA", "EN_TRANSITO", "COMPLETADA", "CANCELADA"

    public Transferencia(String codigoTransferencia, Libro libro, String sucursalOrigen,
                         String sucursalDestino, Usuario solicitante) {
        if (codigoTransferencia == null || codigoTransferencia.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de transferencia no puede ser nulo o vacío");
        }
        if (libro == null) {
            throw new IllegalArgumentException("Libro no puede ser nulo");
        }
        if (sucursalOrigen == null || sucursalOrigen.trim().isEmpty()) {
            throw new IllegalArgumentException("Sucursal origen no puede ser nula o vacía");
        }
        if (sucursalDestino == null || sucursalDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("Sucursal destino no puede ser nula o vacía");
        }
        if (sucursalOrigen.equals(sucursalDestino)) {
            throw new IllegalArgumentException("Las sucursales de origen y destino no pueden ser iguales");
        }

        this.codigoTransferencia = codigoTransferencia;
        this.libro = libro;
        this.sucursalOrigen = sucursalOrigen;
        this.sucursalDestino = sucursalDestino;
        this.solicitante = solicitante;
        this.fechaSolicitud = LocalDateTime.now();
        this.fechaCompletada = null;
        this.estado = "SOLICITADA";
    }

    public boolean iniciarTransferencia() {
        if (!estado.equals("SOLICITADA")) {
            throw new IllegalStateException("La transferencia no está en estado SOLICITADA");
        }

        if (libro.getSucursal().equals(sucursalDestino)) {
            throw new IllegalStateException("El libro ya está en la sucursal destino");
        }

        estado = "EN_TRANSITO";
        libro.setDisponible(false); // No disponible durante transferencia
        return true;
    }

    public boolean completarTransferencia() {
        if (!estado.equals("EN_TRANSITO")) {
            throw new IllegalStateException("La transferencia no está en estado EN_TRANSITO");
        }

        estado = "COMPLETADA";
        fechaCompletada = LocalDateTime.now();
        libro.setSucursal(sucursalDestino);
        libro.setDisponible(true); // Disponible en nueva sucursal
        return true;
    }

    public boolean cancelarTransferencia() {
        if (estado.equals("COMPLETADA")) {
            throw new IllegalStateException("No se puede cancelar una transferencia COMPLETADA");
        }

        estado = "CANCELADA";
        libro.setDisponible(true); // Volver a estar disponible
        return true;
    }

    public String getCodigoTransferencia() {
        return codigoTransferencia;
    }

    public Libro getLibro() {
        return libro;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public String getSucursalDestino() {
        return sucursalDestino;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public boolean estaCompletada() {
        return estado.equals("COMPLETADA");
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigoTransferencia);
        sb.append("\nLibro: ").append(libro.getTitulo());
        sb.append("\nOrigen: ").append(sucursalOrigen);
        sb.append("\nDestino: ").append(sucursalDestino);
        sb.append("\nSolicitante: ").append(solicitante.getNombre());
        sb.append("\nFecha solicitud: ").append(fechaSolicitud.format(formatter));
        sb.append("\nEstado: ").append(estado);

        if (fechaCompletada != null) {
            sb.append("\nFecha completada: ").append(fechaCompletada.format(formatter));
        }

        return sb.toString();
    }
}