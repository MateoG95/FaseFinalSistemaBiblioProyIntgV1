package sistemaBiblio.core;

public class Libro {
    private String isbn;
    private String titulo;
    private String genero;
    private int anio;
    private Autor autor;
    private boolean disponible;
    private String sucursal;

    public Libro(String isbn, String titulo, String genero, int anio, Autor autor, String sucursal) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN no puede ser nulo o vacío");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título no puede ser nulo o vacío");
        }
        if (anio < 0) {
            throw new IllegalArgumentException("Año no puede ser negativo");
        }
        if (autor == null) {
            throw new IllegalArgumentException("Autor no puede ser nulo");
        }
        if (sucursal == null || sucursal.trim().isEmpty()) {
            throw new IllegalArgumentException("Sucursal no puede ser nula o vacía");
        }

        this.isbn = isbn;
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.autor = autor;
        this.sucursal = sucursal;
        this.disponible = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnio() {
        return anio;
    }

    public Autor getAutor() {
        return autor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        String disponibilidad = disponible ? "Disponible" : "No disponible";
        return "ISBN: " + isbn +
                " - Título: " + titulo +
                " - Autor: " + autor.getNombre() +
                " - Género: " + genero +
                " - Sucursal: " + sucursal +
                " - Estado: " + disponibilidad;
    }
}