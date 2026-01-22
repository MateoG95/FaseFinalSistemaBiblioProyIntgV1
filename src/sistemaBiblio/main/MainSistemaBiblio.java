package sistemaBiblio.main;

import sistemaBiblio.sistema.GestorBiblioteca;
import sistemaBiblio.sucursal.Sucursal;
import sistemaBiblio.sucursal.SucursalGranados;
import sistemaBiblio.sucursal.SucursalPark;

import java.util.Scanner;

public class MainSistemaBiblio {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("------- SISTEMA DE GESTION DE BIBLIOTECA UDLA -------");
        System.out.println("-----------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);

        try {
            // Crear ambas sucursales una vez al inicio
            SucursalPark sucursalPark = new SucursalPark();
            SucursalGranados sucursalGranados = new SucursalGranados();

            // Menú principal para seleccionar/alternar sucursales
            Sucursal sucursalSeleccionada = null;
            GestorBiblioteca gestor = null;

            while (true) {
                if (sucursalSeleccionada == null) {
                    // Primera vez seleccionando sucursal
                    System.out.println("\nSeleccione la sucursal:");
                    System.out.println("1. Biblioteca Park");
                    System.out.println("2. Biblioteca Granados");
                    System.out.println("0. Salir del sistema");
                    System.out.print("Opcion: ");

                    int opcionSucursal = Integer.parseInt(scanner.nextLine());

                    switch (opcionSucursal) {
                        case 1:
                            sucursalSeleccionada = sucursalPark;
                            gestor = new GestorBiblioteca(sucursalSeleccionada, sucursalPark, sucursalGranados);
                            break;
                        case 2:
                            sucursalSeleccionada = sucursalGranados;
                            gestor = new GestorBiblioteca(sucursalSeleccionada, sucursalPark, sucursalGranados);
                            break;
                        case 0:
                            System.out.println("Saliendo del sistema...");
                            return;
                        default:
                            System.out.println("Opcion no valida.");
                            continue;
                    }
                }

                System.out.println("\nBienvenido a " + sucursalSeleccionada.getNombre());
                System.out.println(sucursalSeleccionada);

                // Mostrar menu del gestor
                gestor.mostrarMenu();

                // Después de salir del menú del gestor, preguntar si cambiar de sucursal
                System.out.println("\n¿Desea cambiar de sucursal?");
                System.out.println("1. Si, cambiar de sucursal");
                System.out.println("2. No, continuar en " + sucursalSeleccionada.getNombre());
                System.out.println("0. Salir del sistema");
                System.out.print("Opcion: ");

                int opcionCambio = Integer.parseInt(scanner.nextLine());

                switch (opcionCambio) {
                    case 1:
                        // Cambiar a la otra sucursal
                        if (sucursalSeleccionada.getCodigo().equals("PARK")) {
                            sucursalSeleccionada = sucursalGranados;
                        } else {
                            sucursalSeleccionada = sucursalPark;
                        }
                        gestor = new GestorBiblioteca(sucursalSeleccionada, sucursalPark, sucursalGranados);
                        break;
                    case 2:
                        // Continuar en la misma sucursal
                        break;
                    case 0:
                        System.out.println("\n--- SISTEMA FINALIZADO ---");
                        return;
                    default:
                        System.out.println("Opcion no valida, continuando en la misma sucursal.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero valido");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\n--- SISTEMA FINALIZADO ---");
        }
    }
}