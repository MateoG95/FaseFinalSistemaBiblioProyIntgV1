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
        int opcionSucursal;
        Sucursal sucursalSeleccionada = null;

        try {
            // Seleccionar sucursal
            System.out.println("Seleccione la sucursal:");
            System.out.println("1. Biblioteca Park");
            System.out.println("2. Biblioteca Granados");
            System.out.print("Opcion: ");

            opcionSucursal = Integer.parseInt(scanner.nextLine());

            switch (opcionSucursal) {
                case 1:
                    sucursalSeleccionada = new SucursalPark();
                    break;
                case 2:
                    sucursalSeleccionada = new SucursalGranados();
                    break;
                default:
                    System.out.println("Opcion no valida. Saliendo del sistema.");
                    return;
            }

            System.out.println("\nBienvenido a " + sucursalSeleccionada.getNombre());
            System.out.println(sucursalSeleccionada);

            // Crear gestor para la sucursal seleccionada
            GestorBiblioteca gestor = new GestorBiblioteca(sucursalSeleccionada);

            // Mostrar menu principal
            gestor.mostrarMenu();

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