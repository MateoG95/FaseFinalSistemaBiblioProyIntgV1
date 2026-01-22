package sistemaBiblio.sistema;

import sistemaBiblio.core.Libro;
import sistemaBiblio.multas.Multa;
import sistemaBiblio.prestamos.Prestamo;
import sistemaBiblio.sucursal.Sucursal;
import sistemaBiblio.sucursal.SucursalGranados;
import sistemaBiblio.sucursal.SucursalPark;
import sistemaBiblio.transferencia.Transferencia;
import sistemaBiblio.usuario.Administrador;
import sistemaBiblio.usuario.Docente;
import sistemaBiblio.usuario.Estudiante;
import sistemaBiblio.usuario.Usuario;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;


public class GestorBiblioteca {
    private Sucursal sucursalActual;
    private Sucursal sucursalPark;
    private Sucursal sucursalGranados;
    private ArrayList<Usuario> usuariosRegistrados;
    private Scanner scanner;
    private DateTimeFormatter fechaFormatter;


    public GestorBiblioteca(Sucursal sucursalActual, Sucursal sucursalPark, Sucursal sucursalGranados) {
        if (sucursalActual == null || sucursalPark == null || sucursalGranados == null) {
            throw new IllegalArgumentException("Sucursales no pueden ser nulas");
        }

        this.sucursalActual = sucursalActual;
        this.sucursalPark = sucursalPark;
        this.sucursalGranados = sucursalGranados;
        this.usuariosRegistrados = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        inicializarUsuarios();
    }

    private void inicializarUsuarios() {
        try {
            // Crear estudiantes
            usuariosRegistrados.add(new Estudiante("12345678", "Juan Perez", "3001234567",
                    "juan@udla.edu.ec", "A00108369", "Ingenieria", 5));
            usuariosRegistrados.add(new Estudiante("11223344", "Carlos Lopez", "3001122334",
                    "carlos@udla.edu.ec", "A00117569", "Medicina", 3));
            usuariosRegistrados.add(new Estudiante("99887766", "Laura Martinez", "3009988776",
                    "laura@udla.edu.ec", "A00117571", "Derecho", 7));

            // Crear docentes
            usuariosRegistrados.add(new Docente("87654321", "Maria Garcia", "3007654321",
                    "maria@udla.edu.ec", "D00117568", "Ciencias", "PhD", true));
            usuariosRegistrados.add(new Docente("55667788", "Ana Rodriguez", "3005566778",
                    "ana@udla.edu.ec", "D00117570", "Humanidades", "MSc", false));
            usuariosRegistrados.add(new Docente("33445566", "Pedro Sanchez", "3003344556",
                    "pedro@udla.edu.ec", "D00117572", "Ingenieria", "PhD", true));

            // Crear administradores
            usuariosRegistrados.add(new Administrador("99999999", "Admin Sistema", "3009999999",
                    "admin@udla.edu.ec", "ADM001", "ALTO", "Sistemas"));
            usuariosRegistrados.add(new Administrador("88888888", "Admin Biblioteca", "3008888888",
                    "biblioteca@udla.edu.ec", "ADM002", "MEDIO", "Biblioteca"));

        } catch (IllegalArgumentException e) {
            System.out.println("Error al inicializar usuarios: " + e.getMessage());
        }
    }

    // METODO PRINCIPAL DEL MENU
    public void mostrarMenu() {
        int opc=0;
        do {
            try {
                System.out.println("\n-------SISTEMA DE GESTION DE BIBLIOTECA-------");
                System.out.println("SUCURSAL: " + sucursalActual.getNombre());
                System.out.println("1. Ver Catalogo por Sucursal");
                System.out.println("2. Ver Libros Disponibles");
                System.out.println("3. Prestar Libro");
                System.out.println("4. Entregar Libro");
                System.out.println("5. Transferir libros entre Sucursales");
                System.out.println("6. Listar Multas");
                System.out.println("7. Pagar Multas");
                System.out.println("8. Listar Prestamos Activos");
                System.out.println("9. Ver Usuarios");
                System.out.println("10. Buscar Usuario");
                System.out.println("11. Salir");
                System.out.print("Seleccione una opcion: ");

                opc = Integer.parseInt(scanner.nextLine());

                switch (opc) {
                    case 1:
                        verCatalogoSucursal();
                        break;
                    case 2:
                        verLibrosDisponibles();
                        break;
                    case 3:
                        prestarLibro();
                        break;
                    case 4:
                        entregarLibro();
                        break;
                    case 5:
                        transferirLibro();
                        break;
                    case 6:
                        listarMultas();
                        break;
                    case 7:
                        pagarMulta();
                        break;
                    case 8:
                        listarPrestamosActivos();
                        break;
                    case 9:
                        verUsuariosInterfaz();
                        break;
                    case 10:
                        buscarUsuario();
                        break;
                    case 11:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero valido");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (opc != 11);
    }

    private void verCatalogoSucursal() {
        try {
            System.out.println("\n--- VER CATALOGO POR SUCURSAL ---");
            System.out.println("1. Catalogo " + sucursalActual.getNombre());
            System.out.println("2. Catalogo de la otra sucursal");
            System.out.println("3. Ambas sucursales");
            System.out.print("Seleccione una opcion: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    mostrarCatalogoSucursal(sucursalActual);
                    break;
                case 2:
                    mostrarCatalogoOtraSucursal();
                    break;
                case 3:
                    mostrarCatalogoAmbasSucursales();
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarCatalogoSucursal(Sucursal sucursal) {
        System.out.println("\n--- CATALOGO DE " + sucursal.getNombre() + " ---");
        System.out.println("Direccion: " + sucursal.getDireccion());
        System.out.println("Telefono: " + sucursal.getTelefono());
        System.out.println("Codigo: " + sucursal.getCodigo());
        System.out.println("-----------------------------------");

        ArrayList<Libro> libros = sucursal.getLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el catalogo");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro);
            System.out.println("---");
        }
    }

    private void mostrarCatalogoOtraSucursal() {
        Sucursal otraSucursal;
        if (sucursalActual.getCodigo().equals("PARK")) {
            otraSucursal = sucursalGranados;
        } else {
            otraSucursal = sucursalPark;
        }
        mostrarCatalogoSucursal(otraSucursal);
    }

    private void mostrarCatalogoAmbasSucursales() {
        mostrarCatalogoSucursal(sucursalPark);
        System.out.println("\n");
        mostrarCatalogoSucursal(sucursalGranados);
    }

    private void verLibrosDisponibles() {
        System.out.println("\n--- LIBROS DISPONIBLES EN " + sucursalActual.getNombre() + " ---");
        ArrayList<Libro> disponibles = sucursalActual.getLibrosDisponibles();

        if (disponibles.isEmpty()) {
            System.out.println("No hay libros disponibles en esta sucursal");
            return;
        }

        for (Libro libro : disponibles) {
            System.out.println(libro);
            System.out.println("---");
        }
    }

    private void prestarLibro() {
        try {
            System.out.println("\n--- PRESTAR LIBRO ---");

            // Buscar usuario con validacion
            Usuario usuario = null;
            while (usuario == null) {
                System.out.print("Ingrese codigo de usuario (0 para salir): ");
                String codigoUsuario = scanner.nextLine();

                if (codigoUsuario.equals("0")) {
                    System.out.println("Operacion cancelada");
                    return;
                }

                usuario = buscarUsuarioPorCodigo(codigoUsuario);
                if (usuario == null) {
                    System.out.println("Usuario no encontrado. Intente nuevamente.");
                }
            }

            // Verificar si puede prestar
            if (!usuario.puedeRealizarPrestamo()) {
                System.out.println("\nEl usuario no puede realizar prestamos:");
                if (usuario.tieneMultasActivas()) {
                    System.out.println("- Tiene " + usuario.getMultasActivas().size() + " multas activas");
                }
                System.out.println("- El usuario se encuentra sancionado hasta completar el periodo de multa");
                System.out.println("Para poder prestar, debe pagar las multas activas o hasta que pase la fecha de sancion");
                return;
            }

            // Verificar limite de prestamos
            if (usuario.getCantidadPrestamosActivos() >= usuario.getMaxLibrosPrestados()) {
                System.out.println("El usuario ha alcanzado su limite de " + usuario.getMaxLibrosPrestados() + " prestamos");
                return;
            }

            char continuar = 'S';
            do {
                // Mostrar libros disponibles
                System.out.println("\nLibros disponibles en " + sucursalActual.getNombre() + ":");
                ArrayList<Libro> disponibles = sucursalActual.getLibrosDisponibles();

                if (disponibles.isEmpty()) {
                    System.out.println("No hay libros disponibles");
                    return;
                }

                for (Libro libro : disponibles) {
                    System.out.println(libro);
                    System.out.println("---");
                }

                // Seleccionar libro
                System.out.print("\nIngrese ISBN del libro: ");
                String isbn = scanner.nextLine();

                Libro libro = sucursalActual.buscarLibroPorISBN(isbn);
                if (libro == null) {
                    System.out.println("Libro no encontrado");
                } else if (!libro.isDisponible()) {
                    System.out.println("El libro no esta disponible");
                } else if (!libro.getSucursal().equals(sucursalActual.getCodigo())) {
                    System.out.println("El libro no esta en esta sucursal");
                } else {
                    // Calcular fecha de devolucion basada en tipo de usuario
                    LocalDate fechaDevolucion;
                    if (usuario instanceof Docente) {
                        Docente docente = (Docente) usuario;
                        fechaDevolucion = LocalDate.now().plusDays(docente.getDiasPrestamoInvestigacion());
                        System.out.println("Docente - Prestamo por " + docente.getDiasPrestamoInvestigacion() + " dias");
                    } else if (usuario instanceof Estudiante) {
                        // Estudiante: 15 dias
                        fechaDevolucion = LocalDate.now().plusDays(15);
                        System.out.println("Estudiante - Prestamo por 15 dias");
                    } else {
                        // Administrador: 30 dias
                        fechaDevolucion = LocalDate.now().plusDays(30);
                        System.out.println("Administrador - Prestamo por 30 dias");
                    }

                    // Crear prestamo
                    String codigoPrestamo = sucursalActual.getCodigo() + "-PRE" + (sucursalActual.getPrestamos().size() + 1);
                    Prestamo prestamo = new Prestamo(codigoPrestamo, usuario, libro, fechaDevolucion, sucursalActual.getCodigo());
                    sucursalActual.agregarPrestamo(prestamo);

                    System.out.println("\nPrestamo realizado exitosamente");
                    System.out.println("Codigo de prestamo: " + codigoPrestamo);
                    System.out.println("Fecha devolucion: " + fechaDevolucion.format(fechaFormatter));
                    System.out.println("Libro: " + libro.getTitulo());

                }

                // Validacion de respuesta S/N
                boolean respuestaValida = false;
                while (!respuestaValida) {
                    try {
                        System.out.print("\nDesea prestar otro libro? (S/N): ");
                        String respuesta = scanner.nextLine().toUpperCase();

                        if (respuesta.length() != 1) {
                            throw new IllegalArgumentException("Debe ingresar solo un caracter");
                        }

                        continuar = respuesta.charAt(0);
                        if (continuar != 'S' && continuar != 'N') {
                            throw new IllegalArgumentException("Debe ingresar 'S' o 'N'");
                        }

                        respuestaValida = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage() + ". Intente nuevamente.");
                    }
                }

            } while (continuar == 'S');

        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha invalido");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al realizar prestamo: " + e.getMessage());
        }
    }

    private void entregarLibro() {
        try {
            System.out.println("\n--- ENTREGAR LIBRO ---");

            // Buscar usuario con validacion
            Usuario usuario = null;
            while (usuario == null) {
                System.out.print("Ingrese codigo de usuario (0 para salir): ");
                String codigoUsuario = scanner.nextLine();

                if (codigoUsuario.equals("0")) {
                    System.out.println("Operacion cancelada");
                    return;
                }

                usuario = buscarUsuarioPorCodigo(codigoUsuario);
                if (usuario == null) {
                    System.out.println("Usuario no encontrado. Intente nuevamente.");
                }
            }

            // Mostrar prestamos activos del usuario
            System.out.println("\nPrestamos activos de " + usuario.getNombre() + ":");
            ArrayList<Prestamo> prestamosActivos = sucursalActual.getPrestamosActivos();
            boolean tienePrestamos = false;

            for (Prestamo prestamo : prestamosActivos) {
                if (prestamo.getUsuario().equals(usuario) && !prestamo.isDevuelto()) {
                    System.out.println(prestamo);
                    System.out.println("---");
                    tienePrestamos = true;
                }
            }

            if (!tienePrestamos) {
                System.out.println("El usuario no tiene prestamos activos");
                return;
            }

            // Seleccionar prestamo a devolver
            System.out.print("Ingrese codigo del prestamo a devolver: ");
            String codigoPrestamo = scanner.nextLine();

            Prestamo prestamoSeleccionado = null;
            for (Prestamo prestamo : prestamosActivos) {
                if (prestamo.getCodigoPrestamo().equals(codigoPrestamo) &&
                        prestamo.getUsuario().equals(usuario) &&
                        !prestamo.isDevuelto()) {
                    prestamoSeleccionado = prestamo;
                    break;
                }
            }

            if (prestamoSeleccionado == null) {
                System.out.println("Prestamo no encontrado o ya devuelto");
                return;
            }

            // Ingresar fecha real de devolucion con validacion
            LocalDate fechaReal = null;
            boolean fechaValida = false;

            while (!fechaValida) {
                try {
                    System.out.print("Ingrese fecha real de devolucion (dd/mm/aaaa): ");
                    String fechaStr = scanner.nextLine();
                    fechaReal = LocalDate.parse(fechaStr, fechaFormatter);
                    fechaValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Error: Formato de fecha invalido. Use dd/mm/aaaa");
                }
            }

            // Realizar devolucion
            boolean devolucionATiempo = prestamoSeleccionado.devolver(fechaReal);

            if (devolucionATiempo) {
                System.out.println("Libro devuelto a tiempo. Gracias!");
            } else {
                System.out.println("Libro devuelto con retraso. Se ha aplicado una multa.");
                // Agregar multa al sistema
                if (prestamoSeleccionado.tieneMulta()) {
                    sucursalActual.agregarMulta(prestamoSeleccionado.getMulta());
                }
            }

        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al entregar libro: " + e.getMessage());
        }
    }

    private void transferirLibro() {
        try {
            System.out.println("\n--- TRANSFERIR LIBRO ENTRE SUCURSALES ---");

            // Validar que el usuario sea administrador
            System.out.print("Ingrese codigo de usuario administrador: ");
            String codigoUsuario = scanner.nextLine();

            Usuario usuario = buscarUsuarioPorCodigo(codigoUsuario);
            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            if (!(usuario instanceof Administrador)) {
                System.out.println("Error: Solo los administradores pueden realizar transferencias");
                return;
            }

            Administrador admin = (Administrador) usuario;
            if (!admin.puedeRealizarTransferencia()) {
                System.out.println("Error: Este administrador no tiene permisos para transferir");
                return;
            }

            // Mostrar libros disponibles en esta sucursal
            System.out.println("\nLibros disponibles en " + sucursalActual.getNombre() + ":");
            ArrayList<Libro> disponibles = sucursalActual.getLibrosDisponibles();

            if (disponibles.isEmpty()) {
                System.out.println("No hay libros disponibles para transferir");
                return;
            }

            for (Libro libro : disponibles) {
                System.out.println(libro);
                System.out.println("---");
            }

            // Seleccionar libro
            System.out.print("Ingrese ISBN del libro a transferir: ");
            String isbn = scanner.nextLine();

            Libro libro = sucursalActual.buscarLibroPorISBN(isbn);
            if (libro == null) {
                System.out.println("Libro no encontrado en esta sucursal");
                return;
            }

            if (!libro.isDisponible()) {
                System.out.println("El libro no esta disponible para transferencia");
                return;
            }

            // Obtener sucursal destino
            Sucursal sucursalDestino;
            if (sucursalActual.getCodigo().equals("PARK")) {
                sucursalDestino = sucursalGranados;
            } else {
                sucursalDestino = sucursalPark;
            }

            System.out.println("\nTransferencia programada:");
            System.out.println("Sucursal origen: " + sucursalActual.getNombre());
            System.out.println("Sucursal destino: " + sucursalDestino.getNombre());
            System.out.println("Libro: " + libro.getTitulo());
            System.out.println("Administrador: " + admin.getNombre());

            // Confirmar transferencia
            System.out.print("\nConfirmar transferencia? (S/N): ");
            String confirmacion = scanner.nextLine().toUpperCase();

            if (!confirmacion.equals("S")) {
                System.out.println("Transferencia cancelada");
                return;
            }

            // Crear transferencia
            String codigoTransferencia = "TRF-" + sucursalActual.getCodigo() + "-" +
                    (sucursalActual.getTransferencias().size() + 1);

            Transferencia transferencia = new Transferencia(
                    codigoTransferencia, libro, sucursalActual.getCodigo(),
                    sucursalDestino.getCodigo(), admin
            );

            // Iniciar transferencia
            boolean iniciada = transferencia.iniciarTransferencia();
            if (iniciada) {
                // Agregar transferencia a ambas sucursales
                sucursalActual.getTransferencias().add(transferencia);
                sucursalDestino.getTransferencias().add(transferencia);

                System.out.println("Transferencia iniciada exitosamente");
                System.out.println("Codigo de transferencia: " + codigoTransferencia);
                System.out.println("El libro estara en transito por 2 dias habiles");

                // Preguntar si completar ahora
                System.out.print("Marcar transferencia como completada ahora? (S/N): ");
                String completarAhora = scanner.nextLine().toUpperCase();

                if (completarAhora.equals("S")) {
                    boolean completada = transferencia.completarTransferencia();
                    if (completada) {
                        // IMPORTANTE: Remover libro de sucursal origen y agregar a destino
                        boolean removido = sucursalActual.getLibros().remove(libro);
                        if (removido) {
                            libro.setSucursal(sucursalDestino.getCodigo());
                            libro.setDisponible(true);
                            sucursalDestino.agregarLibro(libro);
                            System.out.println("Transferencia completada exitosamente");
                            System.out.println("El libro ahora esta disponible en " + sucursalDestino.getNombre());
                        }
                    }
                } else {
                    System.out.println("Transferencia quedara como EN TRANSITO");
                    System.out.println("Debe completarla manualmente posteriormente");
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al transferir libro: " + e.getMessage());
        }
    }

    private void listarMultas() {
        System.out.println("\n--- MULTAS ACTIVAS EN " + sucursalActual.getNombre() + " ---");
        ArrayList<Multa> multasActivas = sucursalActual.getMultasActivas();

        if (multasActivas.isEmpty()) {
            System.out.println("No hay multas activas en esta sucursal");
            return;
        }

        for (Multa multa : multasActivas) {
            System.out.println(multa);
            System.out.println("---");
        }
    }

    private void pagarMulta() {
        try {
            // Mostrar ventana emergente de inicio
            JOptionPane.showMessageDialog(null,
                    "=== PAGO DE MULTAS ===\nSucursal: " + sucursalActual.getNombre(),
                    "Sistema de Biblioteca",
                    JOptionPane.INFORMATION_MESSAGE);

            // Listar multas activas
            ArrayList<Multa> multasActivas = sucursalActual.getMultasActivas();
            if (multasActivas.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No hay multas activas para pagar en esta sucursal.",
                        "Informacion",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Crear lista de multas para mostrar en ventana
            StringBuilder listaMultas = new StringBuilder();
            listaMultas.append("=== MULTAS ACTIVAS ===\n\n");

            for (int i = 0; i < multasActivas.size(); i++) {
                Multa multa = multasActivas.get(i);
                listaMultas.append((i + 1) + ". ").append(multa.getCodigoMulta())
                        .append(" - ").append(multa.getUsuario().getNombre())
                        .append("\n   Monto: $").append(String.format("%.2f", multa.getMonto()))
                        .append(" - Motivo: ").append(multa.getMotivo())
                        .append("\n\n");
            }

            // Mostrar lista de multas
            JOptionPane.showMessageDialog(null,
                    listaMultas.toString(),
                    "Multas Activas",
                    JOptionPane.INFORMATION_MESSAGE);

            // Pedir codigo de multa a pagar
            String codigoMulta = JOptionPane.showInputDialog(null,
                    "Ingrese el codigo de la multa a pagar:",
                    "Seleccionar Multa",
                    JOptionPane.QUESTION_MESSAGE);

            // Validar si se cancelo
            if (codigoMulta == null || codigoMulta.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Operacion cancelada por el usuario.",
                        "Cancelado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Buscar multa seleccionada
            Multa multaSeleccionada = null;
            for (Multa multa : multasActivas) {
                if (multa.getCodigoMulta().equals(codigoMulta.trim()) && !multa.isPagada()) {
                    multaSeleccionada = multa;
                    break;
                }
            }

            if (multaSeleccionada == null) {
                JOptionPane.showMessageDialog(null,
                        "Multa no encontrada o ya esta pagada.\nCodigo: " + codigoMulta,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mostrar detalle de la multa seleccionada
            String detalleMulta = "=== DETALLE DE MULTA ===\n\n" +
                    "Codigo: " + multaSeleccionada.getCodigoMulta() + "\n" +
                    "Usuario: " + multaSeleccionada.getUsuario().getNombre() + "\n" +
                    "Cedula: " + multaSeleccionada.getUsuario().getCedula() + "\n" +
                    "Monto: $" + String.format("%.2f", multaSeleccionada.getMonto()) + "\n" +
                    "Motivo: " + multaSeleccionada.getMotivo() + "\n" +
                    "Fecha aplicacion: " +
                    multaSeleccionada.getFechaAplicacion().format(fechaFormatter);

            JOptionPane.showMessageDialog(null,
                    detalleMulta,
                    "Detalle de Multa",
                    JOptionPane.INFORMATION_MESSAGE);

            // Confirmar pago
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Confirmar pago de $" + String.format("%.2f", multaSeleccionada.getMonto()) +
                            " por la multa " + multaSeleccionada.getCodigoMulta() + "?\n" +
                            "Usuario: " + multaSeleccionada.getUsuario().getNombre(),
                    "Confirmar Pago",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    // Realizar pago
                    boolean pagada = multaSeleccionada.pagar();

                    if (pagada) {
                        // Mostrar mensaje de exito
                        JOptionPane.showMessageDialog(null,
                                "=== PAGO EXITOSO ===\n\n" +
                                        "Multa pagada correctamente.\n" +
                                        "Codigo: " + multaSeleccionada.getCodigoMulta() + "\n" +
                                        "Monto pagado: $" + String.format("%.2f", multaSeleccionada.getMonto()) + "\n" +
                                        "Fecha pago: " + LocalDate.now().format(fechaFormatter) + "\n\n" +
                                        "El usuario " + multaSeleccionada.getUsuario().getNombre() +
                                        " ahora puede realizar prestamos.",
                                "Pago Completado",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Preguntar si desea imprimir comprobante (simulado)
                        int imprimir = JOptionPane.showConfirmDialog(null,
                                "¿Desea imprimir comprobante de pago?",
                                "Comprobante",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);

                        if (imprimir == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null,
                                    "Comprobante generado exitosamente.\n" +
                                            "Codigo transaccion: PAGO-" + multaSeleccionada.getCodigoMulta() + "-" +
                                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                                    "Comprobante",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Error al procesar el pago.\n" +
                                        "Por favor, intente nuevamente.",
                                "Error en Pago",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalStateException e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: " + e.getMessage(),
                            "Error en Pago",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Pago cancelado por el usuario.",
                        "Pago Cancelado",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error inesperado al procesar el pago:\n" + e.getMessage(),
                    "Error del Sistema",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarPrestamosActivos() {
        System.out.println("\n--- PRESTAMOS ACTIVOS EN " + sucursalActual.getNombre() + " ---");
        ArrayList<Prestamo> prestamosActivos = sucursalActual.getPrestamosActivos();

        if (prestamosActivos.isEmpty()) {
            System.out.println("No hay prestamos activos en esta sucursal");
            return;
        }

        for (Prestamo prestamo : prestamosActivos) {
            System.out.println(prestamo);
            System.out.println("---");
        }
    }

    private void verUsuariosInterfaz() {
        try {
            // Interfaz grafica simulada
            System.out.println("\n-------------------------------------------");
            System.out.println("        INTERFAZ GRAFICA DE USUARIOS      ");
            System.out.println("-------------------------------------------");

            System.out.println("\nOpciones de busqueda:");
            System.out.println("1. Buscar por codigo de usuario");
            System.out.println("2. Buscar por cedula");
            System.out.println("3. Buscar por nombre");
            System.out.println("4. Filtrar por tipo de usuario");
            System.out.println("5. Ver todos los usuarios");
            System.out.println("6. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    buscarUsuarioPorCodigoInterfaz();
                    break;
                case 2:
                    buscarUsuarioPorCedulaInterfaz();
                    break;
                case 3:
                    buscarUsuarioPorNombreInterfaz();
                    break;
                case 4:
                    filtrarUsuariosPorTipoInterfaz();
                    break;
                case 5:
                    mostrarTodosUsuariosInterfaz();
                    break;
                case 6:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido");
        } catch (Exception e) {
            System.out.println("Error en la interfaz: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorCodigoInterfaz() {
        System.out.println("\n--- BUSCAR USUARIO POR CODIGO ---");
        System.out.print("Ingrese codigo de usuario: ");
        String codigo = scanner.nextLine();

        Usuario usuario = buscarUsuarioPorCodigo(codigo);
        if (usuario != null) {
            mostrarInformacionUsuarioInterfaz(usuario);
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

    private void buscarUsuarioPorCedulaInterfaz() {
        System.out.println("\n--- BUSCAR USUARIO POR CEDULA ---");
        System.out.print("Ingrese numero de cedula: ");
        String cedula = scanner.nextLine();

        Usuario usuario = buscarUsuarioPorCedula(cedula);
        if (usuario != null) {
            mostrarInformacionUsuarioInterfaz(usuario);
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

    private void buscarUsuarioPorNombreInterfaz() {
        System.out.println("\n--- BUSCAR USUARIO POR NOMBRE ---");
        System.out.print("Ingrese nombre o parte del nombre: ");
        String nombre = scanner.nextLine();

        ArrayList<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                usuariosEncontrados.add(usuario);
            }
        }

        if (usuariosEncontrados.isEmpty()) {
            System.out.println("No se encontraron usuarios con ese nombre");
        } else {
            System.out.println("\nUsuarios encontrados (" + usuariosEncontrados.size() + "):");
            for (Usuario usuario : usuariosEncontrados) {
                System.out.println("- " + usuario.getNombre() + " (" + usuario.getCodigoUsuario() + ") - " + usuario.getTipo());
            }

            System.out.print("\nVer detalles de un usuario? (Ingrese codigo o 0 para salir): ");
            String opcion = scanner.nextLine();

            if (!opcion.equals("0")) {
                Usuario usuarioSeleccionado = buscarUsuarioPorCodigo(opcion);
                if (usuarioSeleccionado != null && usuariosEncontrados.contains(usuarioSeleccionado)) {
                    mostrarInformacionUsuarioInterfaz(usuarioSeleccionado);
                } else {
                    System.out.println("Codigo no corresponde a los usuarios encontrados");
                }
            }
        }
    }

    private void filtrarUsuariosPorTipoInterfaz() {
        System.out.println("\n--- FILTRAR USUARIOS POR TIPO ---");
        System.out.println("1. Estudiantes");
        System.out.println("2. Docentes");
        System.out.println("3. Administradores");
        System.out.println("4. Todos los tipos");
        System.out.print("Seleccione tipo: ");

        try {
            int tipo = Integer.parseInt(scanner.nextLine());
            String tipoFiltro = "";

            switch (tipo) {
                case 1:
                    tipoFiltro = "Estudiante";
                    break;
                case 2:
                    tipoFiltro = "Docente";
                    break;
                case 3:
                    tipoFiltro = "Administrador";
                    break;
                case 4:
                    tipoFiltro = "Todos";
                    break;
                default:
                    System.out.println("Opcion no valida");
                    return;
            }

            System.out.println("\n--- USUARIOS " + tipoFiltro.toUpperCase() + " ---");
            int contador = 0;

            for (Usuario usuario : usuariosRegistrados) {
                boolean mostrar = false;

                switch (tipoFiltro) {
                    case "Estudiante":
                        mostrar = (usuario instanceof Estudiante);
                        break;
                    case "Docente":
                        mostrar = (usuario instanceof Docente);
                        break;
                    case "Administrador":
                        mostrar = (usuario instanceof Administrador);
                        break;
                    case "Todos":
                        mostrar = true;
                        break;
                }

                if (mostrar) {
                    System.out.println(usuario);
                    System.out.println("---");
                    contador++;
                }
            }

            System.out.println("Total encontrados: " + contador);

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido");
        }
    }

    private void mostrarTodosUsuariosInterfaz() {
        System.out.println("\n--- TODOS LOS USUARIOS REGISTRADOS ---");
        System.out.println("Total: " + usuariosRegistrados.size() + " usuarios");

        for (Usuario usuario : usuariosRegistrados) {
            System.out.println(usuario);
            System.out.println("---");
        }
    }

    private void mostrarInformacionUsuarioInterfaz(Usuario usuario) {
        System.out.println("\n-------------------------------------------");
        System.out.println("       INFORMACION DETALLADA DE USUARIO    ");
        System.out.println("-------------------------------------------");

        System.out.println("\nDATOS PERSONALES:");
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Cedula: " + usuario.getCedula());
        System.out.println("Telefono: " + usuario.getTelefono());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Codigo Usuario: " + usuario.getCodigoUsuario());
        System.out.println("Tipo: " + usuario.getTipo());

        // Informacion especifica por tipo
        System.out.println("\nINFORMACION ESPECIFICA:");
        if (usuario instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuario;
            System.out.println("Carrera: " + estudiante.getCarrera());
            System.out.println("Semestre: " + estudiante.getSemestre());
            System.out.println("Prestamos especiales: " + (estudiante.puedePrestamoEspecial() ? "Si" : "No"));
        } else if (usuario instanceof Docente) {
            Docente docente = (Docente) usuario;
            System.out.println("Departamento: " + docente.getDepartamento());
            System.out.println("Titulo: " + docente.getTitulo());
            System.out.println("Investigador activo: " + (docente.isInvestigadorActivo() ? "Si" : "No"));
            System.out.println("Dias de prestamo: " + docente.getDiasPrestamoInvestigacion());
        } else if (usuario instanceof Administrador) {
            Administrador admin = (Administrador) usuario;
            System.out.println("Nivel acceso: " + admin.getNivelAcceso());
            System.out.println("Departamento: " + admin.getDepartamento());
            System.out.println("Puede transferir: " + (admin.puedeRealizarTransferencia() ? "Si" : "No"));
        }

        // Estadisticas
        System.out.println("\nESTADISTICAS:");
        System.out.println("Prestamos activos: " + usuario.getCantidadPrestamosActivos());
        System.out.println("Total prestamos: " + usuario.getTotalPrestamos());
        System.out.println("Multas activas: " + usuario.getMultasActivas().size());
        System.out.println("Total multas: " + usuario.getTotalMultas());
        System.out.println("Puede realizar prestamos: " + (usuario.puedeRealizarPrestamo() ? "Si" : "No"));

        System.out.println("\n-------------------------------------------");
    }

    private void buscarUsuario() {
        try {
            char continuarBusqueda = 'S';

            do {
                System.out.println("\n--- BUSCAR USUARIO ---");
                System.out.println("Opciones de busqueda:");
                System.out.println("1. Por codigo de usuario");
                System.out.println("2. Por cedula");
                System.out.println("3. Por nombre");
                System.out.print("Seleccione opcion: ");

                int opcionBusqueda = Integer.parseInt(scanner.nextLine());
                String criterio = "";
                Usuario usuarioEncontrado = null;

                switch (opcionBusqueda) {
                    case 1:
                        System.out.print("Ingrese codigo de usuario: ");
                        criterio = scanner.nextLine();
                        usuarioEncontrado = buscarUsuarioPorCodigo(criterio);
                        break;

                    case 2:
                        System.out.print("Ingrese cedula: ");
                        criterio = scanner.nextLine();
                        usuarioEncontrado = buscarUsuarioPorCedula(criterio);
                        break;
                    case 3:
                        System.out.print("Ingrese nombre: ");
                        criterio = scanner.nextLine();
                        usuarioEncontrado = buscarUsuarioPorNombre(criterio);
                        break;

                    default:
                        System.out.println("Opcion no valida");
                        return;
                }

                if (usuarioEncontrado != null) {
                    mostrarInformacionDetalladaUsuario(usuarioEncontrado);
                } else {
                    System.out.println("Usuario no encontrado con el criterio: " + criterio);
                }

                // Validacion de respuesta para continuar busqueda
                boolean respuestaValida = false;
                while (!respuestaValida) {
                    try {
                        System.out.print("\nDesea buscar otro usuario? (S/N): ");
                        String respuesta = scanner.nextLine().toUpperCase();

                        if (respuesta.length() != 1) {
                            throw new IllegalArgumentException("Debe ingresar solo un caracter");
                        }

                        continuarBusqueda = respuesta.charAt(0);
                        if (continuarBusqueda != 'S' && continuarBusqueda != 'N') {
                            throw new IllegalArgumentException("Debe ingresar 'S' o 'N'");
                        }

                        respuestaValida = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage() + ". Intente nuevamente.");
                    }
                }

            } while (continuarBusqueda == 'S');

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido");
        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
    }
    private Usuario buscarUsuarioPorCodigo(String codigoUsuario) {
        if (codigoUsuario == null || codigoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo de usuario no puede ser nulo o vacio");
        }

        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCodigoUsuario().equals(codigoUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("Cedula no puede ser nula o vacia");
        }

        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede ser nulo o vacio");
        }

        ArrayList<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                usuariosEncontrados.add(usuario);
            }
        }

        if (usuariosEncontrados.isEmpty()) {
            return null;
        } else if (usuariosEncontrados.size() == 1) {
            return usuariosEncontrados.get(0);
        } else {
            // Si hay multiples usuarios, mostrar lista y preguntar
            System.out.println("\nSe encontraron " + usuariosEncontrados.size() + " usuarios:");
            for (int i = 0; i < usuariosEncontrados.size(); i++) {
                Usuario usuario = usuariosEncontrados.get(i);
                System.out.println((i + 1) + ". " + usuario.getNombre() + " - " + usuario.getCodigoUsuario() + " - " + usuario.getTipo());
            }

            System.out.print("Seleccione el numero del usuario (0 para cancelar): ");
            try {
                int seleccion = Integer.parseInt(scanner.nextLine());
                if (seleccion == 0) {
                    return null;
                } else if (seleccion >= 1 && seleccion <= usuariosEncontrados.size()) {
                    return usuariosEncontrados.get(seleccion - 1);
                } else {
                    System.out.println("Seleccion no valida");
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero");
                return null;
            }
        }
    }

    private void mostrarInformacionDetalladaUsuario(Usuario usuario) {
        System.out.println("\n--- INFORMACION DETALLADA DEL USUARIO ---");
        System.out.println(usuario);

        // Mostrar prestamos activos del usuario
        ArrayList<Prestamo> prestamosActivos = sucursalActual.getPrestamosActivos();
        int contadorPrestamosUsuario = 0;

        System.out.println("\nPRESTAMOS ACTIVOS EN " + sucursalActual.getNombre() + ":");
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getUsuario().equals(usuario)) {
                System.out.println("- " + prestamo.getLibro().getTitulo() +
                        " (Codigo: " + prestamo.getCodigoPrestamo() +
                        ", Fecha devolucion: " + prestamo.getFechaDevolucion().format(fechaFormatter) + ")");
                contadorPrestamosUsuario++;
            }
        }

        if (contadorPrestamosUsuario == 0) {
            System.out.println("No tiene prestamos activos en esta sucursal");
        } else {
            System.out.println("Total prestamos activos: " + contadorPrestamosUsuario);
        }

        // Mostrar multas activas del usuario
        ArrayList<Multa> multasActivas = sucursalActual.getMultasActivas();
        int contadorMultasUsuario = 0;

        System.out.println("\nMULTAS ACTIVAS EN " + sucursalActual.getNombre() + ":");
        for (Multa multa : multasActivas) {
            if (multa.getUsuario().equals(usuario)) {
                System.out.println("- $" + multa.getMonto() + " - " + multa.getMotivo() +
                        " (Codigo: " + multa.getCodigoMulta() +
                        ", Fecha: " + multa.getFechaAplicacion().format(fechaFormatter) + ")");
                contadorMultasUsuario++;
            }
        }

        if (contadorMultasUsuario == 0) {
            System.out.println("No tiene multas activas en esta sucursal");
        } else {
            System.out.println("Total multas activas: " + contadorMultasUsuario);
        }

        // Mostrar informacion especifica segun tipo
        System.out.println("\nINFORMACION ESPECIFICA:");
        if (usuario instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuario;
            System.out.println("Tipo: Estudiante");
            System.out.println("Carrera: " + estudiante.getCarrera());
            System.out.println("Semestre: " + estudiante.getSemestre());
            System.out.println("Puede prestamos especiales: " +
                    (estudiante.puedePrestamoEspecial() ? "Si" : "No"));
            System.out.println("Limite de prestamos: " + estudiante.getMaxLibrosPrestados());
        } else if (usuario instanceof Docente) {
            Docente docente = (Docente) usuario;
            System.out.println("Tipo: Docente");
            System.out.println("Departamento: " + docente.getDepartamento());
            System.out.println("Titulo: " + docente.getTitulo());
            System.out.println("Investigador activo: " + (docente.isInvestigadorActivo() ? "Si" : "No"));
            System.out.println("Dias de prestamo: " + docente.getDiasPrestamoInvestigacion());
            System.out.println("Limite de prestamos: " + docente.getMaxLibrosPrestados());
        } else if (usuario instanceof Administrador) {
            Administrador admin = (Administrador) usuario;
            System.out.println("Tipo: Administrador");
            System.out.println("Nivel acceso: " + admin.getNivelAcceso());
            System.out.println("Departamento: " + admin.getDepartamento());
            System.out.println("Puede transferir libros: " + (admin.puedeRealizarTransferencia() ? "Si" : "No"));
            System.out.println("Limite de prestamos: " + admin.getMaxLibrosPrestados());
        }

        // Mostrar si puede realizar prestamos
        System.out.println("\nESTADO ACTUAL:");
        if (usuario.puedeRealizarPrestamo()) {
            System.out.println("ESTADO: Puede realizar prestamos");
        } else {
            System.out.println("ESTADO: NO puede realizar prestamos");
            if (usuario.tieneMultasActivas()) {
                System.out.println("RAZON: Tiene " + usuario.getMultasActivas().size() + " multas activas");
            }
            System.out.println("SOLUCION: Debe pagar las multas activas o esperar a que pase la fecha de sancion");
        }
    }

    // Metodo para cerrar recursos
    public void cerrarRecursos() {
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }
    }

    // Getters para informacion del sistema
    public String getNombreSucursal() {
        return sucursalActual.getNombre();
    }

    public int getTotalUsuariosRegistrados() {
        return usuariosRegistrados.size();
    }

    public int getTotalEstudiantes() {
        int contador = 0;
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario instanceof Estudiante) {
                contador++;
            }
        }
        return contador;
    }

    public int getTotalDocentes() {
        int contador = 0;
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario instanceof Docente) {
                contador++;
            }
        }
        return contador;
    }

    public int getTotalAdministradores() {
        int contador = 0;
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario instanceof Administrador) {
                contador++;
            }
        }
        return contador;
    }

    public int getTotalLibrosSucursal() {
        return sucursalActual.getLibros().size();
    }

    public int getTotalPrestamosActivos() {
        return sucursalActual.getPrestamosActivos().size();
    }

    public int getTotalMultasActivas() {
        return sucursalActual.getMultasActivas().size();
    }

    public void mostrarResumenSistema() {
        System.out.println("\n--- RESUMEN DEL SISTEMA ---");
        System.out.println("Sucursal: " + getNombreSucursal());
        System.out.println("Total usuarios registrados: " + getTotalUsuariosRegistrados());
        System.out.println("  - Estudiantes: " + getTotalEstudiantes());
        System.out.println("  - Docentes: " + getTotalDocentes());
        System.out.println("  - Administradores: " + getTotalAdministradores());
        System.out.println("Total libros en sucursal: " + getTotalLibrosSucursal());
        System.out.println("Total prestamos activos: " + getTotalPrestamosActivos());
        System.out.println("Total multas activas: " + getTotalMultasActivas());
    }
}