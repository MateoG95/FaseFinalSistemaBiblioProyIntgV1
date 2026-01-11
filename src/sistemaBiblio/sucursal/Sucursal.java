package sistemaBiblio.sucursal;

import sistemaBiblio.core.Libro;
import sistemaBiblio.multas.Multa;
import sistemaBiblio.prestamos.Prestamo;
import sistemaBiblio.transferencia.Transferencia;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class Sucursal {
    protected String nombre;
    protected String codigo;
    protected String direccion;
    protected String telefono;
    protected ArrayList<Libro> libros;
    protected ArrayList<Prestamo> prestamos;
    protected ArrayList<Multa> multas;
    protected ArrayList<Transferencia> transferencias;
    protected DateTimeFormatter fechaFormatter;

    public Sucursal(String nombre, String codigo, String direccion, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de sucursal no puede ser nulo o vacío");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de sucursal no puede ser nulo o vacío");
        }

        this.nombre = nombre;
        this.codigo = codigo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.libros = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.multas = new ArrayList<>();
        this.transferencias = new ArrayList<>();
        this.fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        inicializarDatosSucursal();
    }

    protected abstract void inicializarDatosSucursal();

    // Métodos comunes a todas las sucursales
    public void agregarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("Libro no puede ser nulo");
        }
        libros.add(libro);
    }

    public Libro buscarLibroPorISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN no puede ser nulo o vacío");
        }

        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    public ArrayList<Libro> getLibrosDisponibles() {
        ArrayList<Libro> disponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isDisponible() && libro.getSucursal().equals(codigo)) {
                disponibles.add(libro);
            }
        }
        return disponibles;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        if (prestamo == null) {
            throw new IllegalArgumentException("Préstamo no puede ser nulo");
        }
        prestamos.add(prestamo);
    }

    public ArrayList<Prestamo> getPrestamosActivos() {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            if (!prestamo.isDevuelto()) {
                activos.add(prestamo);
            }
        }
        return activos;
    }

    public void agregarMulta(Multa multa) {
        if (multa == null) {
            throw new IllegalArgumentException("Multa no puede ser nula");
        }
        multas.add(multa);
    }

    public ArrayList<Multa> getMultasActivas() {
        ArrayList<Multa> activas = new ArrayList<>();
        for (Multa multa : multas) {
            if (!multa.isPagada()) {
                activas.add(multa);
            }
        }
        return activas;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public ArrayList<Libro> getLibros() {
        return new ArrayList<>(libros);
    }

    public ArrayList<Prestamo> getPrestamos() {
        return new ArrayList<>(prestamos);
    }

    public ArrayList<Multa> getMultas() {
        return new ArrayList<>(multas);
    }

    public ArrayList<Transferencia> getTransferencias() {
        return new ArrayList<>(transferencias);
    }

    @Override
    public String toString() {
        return "Sucursal: " + nombre +
                " (" + codigo + ")" +
                "\nDirección: " + direccion +
                "\nTeléfono: " + telefono +
                "\nLibros registrados: " + libros.size() +
                "\nPréstamos activos: " + getPrestamosActivos().size() +
                "\nMultas activas: " + getMultasActivas().size();
    }
}