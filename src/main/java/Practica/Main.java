package Practica;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SuministroController sc = new SuministroController();
        Scanner scanner = new Scanner(System.in);
        sc.leerCsv("./compra.csv");
        String respuesta = "";

        do {
            // Menú
            System.out.println("Introduce un comando escribe ayuda para saber los comandos: ");
            respuesta = scanner.nextLine().toLowerCase();
            if (respuesta.equalsIgnoreCase("ayuda")) {
                System.out.println("listar: mostrará todos los suministros que nos quedan.");
                System.out.println("usar x suministro: actualizará la base de datos reduciendo en x el suministo pasado por parámetro. ");
                System.out.println("hay suministro: mostrar cuantos suministros nos quedan.");
                System.out.println("adquirir x suministro: insetará o actualizará el suministro con o en x unidades. ");
                System.out.println("salir: finaliza el programa.");
            }
            if (respuesta.startsWith("usar ")) {
                try {
                    String trozos[] = respuesta.split(" ", 3);
                    int cantidad = Integer.parseInt(trozos[1]);
                    String nombre = trozos[2].trim();

                    // Obtener el suministro por nombre
                    Suministro suministro = sc.getSuministroByName(nombre);

                    if (suministro != null) {
                        // Comprueba si la cantidad especificada es menor o igual que la cantidad disponible
                        if (cantidad < suministro.getCantidad()) {
                            // Actualizar la cantidad del suministro
                            suministro.setCantidad(suministro.getCantidad() - cantidad);
                            sc.updateSuministro(suministro);
                            System.out.println("Se han usado " + cantidad + " unidades del suministro " + nombre);
                        }else if (cantidad == suministro.getCantidad()) {
                            // Borrar el suministro si se usa todo
                            sc.deleteSuministro(suministro.getId());
                            System.out.println("El suministro " + nombre + " ha sido usado en su totalidad y borrado de la lista.");
                        } else {
                            System.out.println("La cantidad de suministros a usar es mayor que la que hay en existencias.");
                        }
                    } else {
                        System.out.println("El suministro " + nombre + " no se encontró.");
                    }

                } catch (Exception e) {
                }

            }
            if (respuesta.equals("listar")) {
                try {
                    List<Suministro> l = sc.listAllSuministros();
                    // Muestra el contenido de la lista
                    for (Suministro s : l) {
                        System.out.println("id: " + s.getId() + ", " + "nombre: " + s.getNombre() + ", " + "cantidad: " + s.getCantidad());
                    }
                } catch (Exception e) {
                }

            }
            if (respuesta.startsWith("hay")) {
                try {
                    String trozos[] = respuesta.split(" ", 2);
                    String nombre = trozos[1];
                    List<Suministro> l = sc.listSuministrosByName(nombre);
                    System.out.println(l);
                    Long id;
                    
                    if (l != null) {

                        for (Suministro s : l) {
                            System.out.println("id: " + s.getId() + ", " + "nombre: " + s.getNombre());
                           
                        }
                        System.out.println("Introduzca el id del suministro del que desee saber la cantidad ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("cantidad: " + sc.getCantidadById(id));

                    } else {
                        System.out.println("no hay nada en la lista");
                    }
                } catch (Exception e) {
                }

            }
            if (respuesta.startsWith("adquirir")) {
                try {
                    String trozos[] = respuesta.split(" ", 3);
                    int cantidad = Integer.parseInt(trozos[1]);
                    String nombre = trozos[2].trim();

                    // Obtener el suministro por nombre
                    Suministro suministro = sc.getSuministroByName(nombre);
                    // Si la consulta nos devuelve un suministro se aumenta la cantidad con un update, si es nulo se crea uno nuevo
                    if (suministro != null) {
                        suministro.setCantidad(suministro.getCantidad() + cantidad);
                        sc.updateSuministro(suministro);
                        System.out.println("Se ha adquirido " + cantidad + " unidades del suministro " + nombre);
                    } else {
                        suministro = new Suministro();
                        suministro.setNombre(nombre);
                        suministro.setCantidad(cantidad);
                        sc.addSuministros(suministro);
                        System.out.println("Se ha adquirido " + cantidad + " unidades del nuevo suministro " + nombre);
                    }

                } catch (Exception e) {
                }

            }

        } while (!respuesta.equals("salir"));

    }
}
