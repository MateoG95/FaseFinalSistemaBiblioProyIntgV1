package sistemaBiblio.sucursal;

import sistemaBiblio.core.Autor;
import sistemaBiblio.core.Libro;

import java.time.LocalTime;

public class SucursalPark extends Sucursal {
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    public SucursalPark() {
        super("Biblioteca Park", "PARK",
                "Av. Granados y Rumichaca", "02-299-0800");
        this.horaApertura = LocalTime.of(8, 0);
        this.horaCierre = LocalTime.of(20, 0);
    }

    @Override
    protected void inicializarDatosSucursal() {
        try {
            Autor autor1 = new Autor("AUT001", "Gabriel Garcia Marquez", "Colombiana");
            Autor autor2 = new Autor("AUT002", "Isabel Allende", "Chilena");
            Autor autor4 = new Autor("AUT004", "Isaac Asimov", "Rusa");

            agregarLibro(new Libro("ISBN001", "Cien anios de soledad", "Novela", 1967, autor1, "PARK"));
            agregarLibro(new Libro("ISBN002", "La casa de los espiritus", "Novela", 1982, autor2, "PARK"));
            agregarLibro(new Libro("ISBN003", "Fundacion", "Ciencia Ficcion", 1951, autor4, "PARK"));
            agregarLibro(new Libro("ISBN007", "El resplandor", "Terror", 1977,
                    new Autor("AUT005", "Stephen King", "Estadounidense"), "PARK"));

        } catch (IllegalArgumentException e) {
            System.out.println("Error al inicializar datos de Park: " + e.getMessage());
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