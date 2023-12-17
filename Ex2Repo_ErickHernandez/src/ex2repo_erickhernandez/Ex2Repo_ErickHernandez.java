package ex2repo_erickhernandez;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Ex2Repo_ErickHernandez {

    public static int vidaHeartless;

    public static void main(String[] args) {

        Teams teams = new Teams();

        teams.Equipos();
        teams.Mochila();

        int cuartoActual = 1;
        while (cuartoActual <= 20 && !teams.party.isEmpty()) {
            int randomEvento = generarRandom(1, 5);

            if (randomEvento == 1) {
                int numeroHeartless = generarRandom(1, 3);
                System.out.println("Te has encontrado con " + numeroHeartless + " Heartless!");
                vidaHeartless = numeroHeartless * 75;
                estadoActual();
                battalla();
            } else if (randomEvento == 2) {
                cofre("Pocion");
            } else if (randomEvento == 3) {
                cofre("Eter");
            } else if (randomEvento == 4) {
                cofre("Elixir");
            } else if (randomEvento == 5) {
                ayudarAmigos();
            }

            cuartoActual++;
        }

        if (cuartoActual > 20) {
            System.out.println("Has ganado!");
        } else {
            System.out.println("Has sido derrotado!");
        }
    }

    public static void battalla() {

        Teams teams = new Teams();

        while (!teams.party.isEmpty() && vidaHeartless > 0) {
            mostrarDetallesEstadoParty();
            int opcion = menu();
            action(opcion);
        }
    }

    public static void mostrarDetallesEstadoParty() {
        System.out.println("");
        System.out.println("Te enfrentas a Heartless con una vida total de " + vidaHeartless);
        System.out.println("");
        estadoActual();
    }

    public static void action(int opcion) {

        Teams teams = new Teams();

        Scanner scanner = new Scanner(System.in);
        int indicePersonaje = opcion;

        if (opcion < 0 || opcion >= teams.party.size()) {
            System.out.println("Opcion invalida. Selecciona un personaje valido.");
            return;
        }

        Personaje personajeSeleccionado = teams.party.get(indicePersonaje);

        System.out.println("Menu:");
        System.out.println("1 - Attack");
        System.out.println("2 - Magic");
        System.out.println("3 - Items");
        System.out.println("4 - Party");

        int opcionAccion = scanner.nextInt();

        switch (opcionAccion) {
            case 1:
                int danio = generarRandom(1, 25);
                System.out.println(personajeSeleccionado.nombre + " ataco y causo " + danio + " de dano a los Heartless" + "\n" + personajeSeleccionado.nombre + " Recibio el golpe");
                dañoHeartless(danio);
                if (vidaHeartless <= 0) {
                    System.out.println("¡Has ganado!");
                }
                recibirDanioParty();
                if (teams.party.isEmpty()) {
                    System.out.println("¡La Party ha sido derrotada! Fin del juego.");
                }
                break;

            case 2:
                System.out.println("1.- Blizzard (50 MP - 50 DMG)");
                System.out.println("2.- Firaga (25 MP - 25 DMG)");
                System.out.println("3.- Gravity (75 MP - 100 DMG)");

                int tipoMagia = scanner.nextInt();

                switch (tipoMagia) {
                    case 1:
                        magia(personajeSeleccionado, "Blizzard", 50, 50);
                        break;

                    case 2:
                        magia(personajeSeleccionado, "Firaga", 25, 25);
                        break;

                    case 3:
                        magia(personajeSeleccionado, "Gravity", 75, 100);
                        break;

                    default:
                        System.out.println("Opcion de magia invalida.");
                        break;
                }
                break;

            case 3:
                mostrarMochila();
                System.out.println("Elige un item:");
                int indiceItem = scanner.nextInt();

                if (indiceItem >= 0 && indiceItem < teams.mochila.size()) {
                    Items itemSeleccionado = teams.mochila.get(indiceItem);
                    usarItem(personajeSeleccionado, itemSeleccionado);

                    teams.mochila.remove(indiceItem);

                    System.out.println(personajeSeleccionado.nombre + " uso " + itemSeleccionado.nombre
                            + " y recupero " + itemSeleccionado.vida + " HP y " + itemSeleccionado.mp + " MP.");
                } else {
                    System.out.println("Opcion de item invalida.");
                }
                break;

            case 4:
                mostrarPersonajesReserva();
                System.out.println("Elige a quien cambiar:");
                int indiceCambio = scanner.nextInt();

                if (indiceCambio >= 0 && indiceCambio < teams.reserva.size()) {
                    cambiarPersonaje(indicePersonaje, indiceCambio);
                    System.out.println(personajeSeleccionado.nombre + " ha sido cambiado antes del ataque!");
                } else {
                    System.out.println("Opcion de cambio invalida.");
                }
                break;

            default:
                System.out.println("Opcion invalida.");
                break;
        }
    }
    
    private static void cofre (String tipoItem) {

        Teams teams = new Teams();

        System.out.println("Te has encontrado un cofre que contiene una " + tipoItem + "!");
        teams.mochila.add(new Items(tipoItem, obtenerHpPoints(tipoItem), obtenerMpPoints(tipoItem)));
        mostrarMochila();
    }

    private static int obtenerHpPoints(String tipoItem) {
        switch (tipoItem.toLowerCase()) {
            case "pocion":
                return 50;
            case "eter":
                return 0;
            case "elixir":
                return 100;
            default:
                return 0;
        }
    }
    
    public static void estadoActual() {

        Teams teams = new Teams();

        System.out.println("Estado actual de la Party:");
        for (int i = 0; i < teams.party.size(); i++) {
            Personaje personaje = teams.party.get(i);
            System.out.println(i + "-" + personaje.nombre + "\n- HP: " + personaje.HP + "\n- MP: " + personaje.MP);
        }
    }

    public static void mostrarPersonajesReserva() {

        Teams teams = new Teams();

        System.out.println("Personajes en reserva:");
        for (int i = 0; i < teams.reserva.size(); i++) {
            Personaje personaje = teams.reserva.get(i);
            System.out.println(i + "- " + personaje.nombre + "\n\tHP=" + personaje.HP + "\n\tMP=" + personaje.MP + "\n\tAttack P. =" + personaje.attackPoints + "\n\tDefense P. =" + personaje.defensePoints);
        }
    }

    public static int generarRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static Items obtenerItem(String tipoItem) {

        Teams teams = new Teams();

        for (int i = 0; i < teams.mochila.size(); i++) {
            Items item = teams.mochila.get(i);
            if (item.nombre.equalsIgnoreCase(tipoItem)) {
                return item;
            }
        }
        return null;
    }

    public static int menu() {
        System.out.println("Elije el personaje:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void mostrarMochila() {

        Teams teams = new Teams();

        System.out.println("Mochila actual:");
        for (int i = 0; i < teams.mochila.size(); i++) {
            Items item = teams.mochila.get(i);
            System.out.println(i + "- " + item.nombre + " HP: " + item.vida + ", MP: " + item.mp);
        }
        System.out.println("");
    }

    private static int obtenerMpPoints(String tipoItem) {
        switch (tipoItem.toLowerCase()) {
            case "pocion":
                return 0;
            case "eter":
                return 50;
            case "elixir":
                return 100;
            default:
                return 0;
        }
    }

    public static void ayudarAmigos() {

        Teams teams = new Teams();

        System.out.println("Te has encontrado amigos que necesitan ayuda y les daras algunos items:");

        if (!teams.mochila.isEmpty()) {
            int cantidadItemsAmigos = generarRandom(1, 3);

            for (int i = 0; i < cantidadItemsAmigos && !teams.mochila.isEmpty(); i++) {
                int index = generarRandom(0, teams.mochila.size() - 1);
                Items itemAmigo = teams.mochila.get(index);
                teams.mochila.remove(index);

                System.out.println("Le has dado a tus amigos: " + itemAmigo.nombre);
                System.out.println("Quedan:");
                mostrarMochila();
                System.out.println("Deseas continuar? (s/n)");

                Scanner scanner = new Scanner(System.in);
                if (!scanner.nextLine().equalsIgnoreCase("s")) {
                    break;
                }
            }
        } else {
            System.out.println("No tienes items para darles a tus amigos.");
        }
    }

    public static void magia(Personaje personaje, String nombreMagia, int costoMP, int danioMagia) {
        if (personaje.MP >= costoMP) {
            personaje.MP -= costoMP;
            System.out.println(personaje.nombre + " lanzo " + nombreMagia + " y causo " + danioMagia + " de dano a los Heartless.");
            dañoHeartless(danioMagia);
        } else {
            System.out.println("No tienes MP suficiente para lanzar " + nombreMagia + ".");
        }
    }

    public static void usarItem(Personaje personaje, Items item) {
        personaje.HP += item.vida;
        personaje.MP += item.mp;
    }

    public static void cambiarPersonaje(int indicePersonaje, int indiceCambio) {

        Teams teams = new Teams();

        Personaje personajeReserva = teams.reserva.get(indiceCambio);
        teams.reserva.remove(indiceCambio);
        teams.reserva.add(teams.party.get(indicePersonaje));
        teams.party.remove(indicePersonaje);
        teams.party.add(personajeReserva);
    }

    public static void dañoHeartless(int danio) {
        vidaHeartless -= danio;
        System.out.println("La vida total de los Heartless es: " + vidaHeartless);
    }

    public static void recibirDanioParty() {

        Teams teams = new Teams();

        for (int i = 0; i < teams.party.size(); i++) {
            Personaje personaje = teams.party.get(i);
            personaje.HP -= generarRandom(1, 10);
        }
        estadoActual();
    }

}
