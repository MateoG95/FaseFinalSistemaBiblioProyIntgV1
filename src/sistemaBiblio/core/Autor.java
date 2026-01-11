package sistemaBiblio.core;

public class Autor {
    private String codigo;
    private String nombre;
    private String nacionalidad;

    public Autor(String codigo, String nombre, String nacionalidad) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del autor no puede ser nulo o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor no puede ser nulo o vacío");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    @Override
    public String toString() {
        return "Autor: " + nombre + " (" + nacionalidad + ") - Código: " + codigo;
    }
}