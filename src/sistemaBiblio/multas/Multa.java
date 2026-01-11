package sistemaBiblio.multas;

import sistemaBiblio.usuario.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Multa {
    private String codigoMulta;
    private Usuario usuario;  // Ahora usa la clase abstracta Usuario
    private double monto;
    private String motivo;
    private LocalDate fechaAplicacion;
    private boolean pagada;
    private LocalDate fechaPago;

    public Multa(String codigoMulta, Usuario usuario, double monto, String motivo) {
        if (codigoMulta == null || codigoMulta.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de multa no puede ser nulo o vacío");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("Monto debe ser mayor a 0");
        }

        this.codigoMulta = codigoMulta;
        this.usuario = usuario;
        this.monto = monto;
        this.motivo = motivo;
        this.fechaAplicacion = LocalDate.now();
        this.pagada = false;
        this.fechaPago = null;
    }

    public boolean pagar() {
        if (pagada) {
            throw new IllegalStateException("La multa ya está pagada");
        }

        this.pagada = true;
        this.fechaPago = LocalDate.now();

        // Eliminar la multa activa del usuario
        return usuario.pagarMulta(codigoMulta);
    }

    public String getCodigoMulta() {
        return codigoMulta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public double getMonto() {
        return monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public boolean isPagada() {
        return pagada;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(codigoMulta);
        sb.append("\nUsuario: ").append(usuario.getNombre());
        sb.append("\nMonto: $").append(String.format("%.2f", monto));
        sb.append("\nMotivo: ").append(motivo);
        sb.append("\nFecha aplicación: ").append(fechaAplicacion.format(formatter));
        sb.append("\nEstado: ").append(pagada ? "PAGADA" : "PENDIENTE");

        if (pagada && fechaPago != null) {
            sb.append("\nFecha pago: ").append(fechaPago.format(formatter));
        }

        return sb.toString();
    }
}