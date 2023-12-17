
package ex2repo_erickhernandez;
import java.util.ArrayList;

public class Teams {
    
    public static ArrayList<Personaje> party = new ArrayList<>();
    public static ArrayList<Personaje> reserva = new ArrayList<>();
    public static ArrayList<Items> mochila = new ArrayList<>();
    
    public void Equipos() {
        party.add(new Personaje("Sora", 300, 300, 75, 15));
        party.add(new Personaje("Donald", 150, 450, 45, 10));
        party.add(new Personaje("Goofy", 450, 100, 150, 50));
    }
    
    public void Reservas () {
        reserva.add(new Personaje("Mickey", 100, 500, 150, 35));
        reserva.add(new Personaje("Roxas", 300, 300, 15, 75));
        reserva.add(new Personaje("Kairi", 200, 200, 50, 15));
    }

    public void Mochila() {
        mochila.add(new Items("Pocion", 50, 0));
        mochila.add(new Items("Eter", 0, 50));
        mochila.add(new Items("Elixir", 100, 100));
    }
}
