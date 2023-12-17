package ex2repo_erickhernandez;

public class Items {
    
    String nombre;
    int vida;
    int mp;

    public Items(String nombre, int vida, int mp) {
        this.nombre = nombre;
        this.vida = vida;
        this.mp = mp;
    }

    public Items() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getvida() {
        return vida;
    }

    public void setvida(int vida) {
        this.vida = vida;
    }

    public int getmp() {
        return mp;
    }

    public void setmp(int mp) {
        this.mp = mp;
    }
    
    public String toString() {
        return "Item{" + "nombre=" + nombre + ", vida=" + vida + ", mp=" + mp + '}';
    }
}
