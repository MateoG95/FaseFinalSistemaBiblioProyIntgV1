package sistemaBiblio.sucursal;

import sistemaBiblio.core.Autor;
import sistemaBiblio.core.Libro;

import java.time.LocalTime;

public class SucursalGranados extends Sucursal {
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    public SucursalGranados() {
        super("Biblioteca Granados", "GRANADOS",
                "Av. Granados 1234", "02-299-0900");
        this.horaApertura = LocalTime.of(7, 30);
        this.horaCierre = LocalTime.of(22, 0);
    }

    @Override
    protected void inicializarDatosSucursal() {
        try {
            Autor autor1 = new Autor("AUT001", "Gabriel Garcia Marquez", "Colombiana");
            Autor autor3 = new Autor("AUT003", "Mario Vargas Llosa", "Peruana");
            Autor autor4 = new Autor("AUT004", "Isaac Asimov", "Rusa");

            agregarLibro(new Libro("ISBN004", "La ciudad y los perros", "Novela", 1963, autor3, "GRANADOS"));
            agregarLibro(new Libro("ISBN005", "El amor en los tiempos del colera", "Novela", 1985, autor1, "GRANADOS"));
            agregarLibro(new Libro("ISBN006", "Yo, robot", "Ciencia Ficcion", 1950, autor4, "GRANADOS"));
            agregarLibro(new Libro("ISBN008", "It", "Terror", 1986,
                    new Autor("AUT005", "Stephen King", "Estadounidense"), "GRANADOS"));

        } catch (IllegalArgumentException e) {
            System.out.println("Error al inicializar datos de Granados: " + e.getMessage());
        }
    }

    public boolean estaAbierta() {
        LocalTime ahora = LocalTime.now();
        return !ahora.isBefore(horaApertura) && !ahora.isAfter(horaCierre);
    }

    public String getHorarioAtencion() {
        return horaApertura + " - " + horaCierre;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nHorario: " + getHorarioAtencion() +
                "\nEstado: " + (estaAbierta() ? "Abierta" : "Cerrada");
    }
}