package cl.duoc.veterinaria.model;

public class Paciente {
    private String Nombre;
    private String Especie;
    private int Edad;
    private String Dueño;

    public Paciente(String nombre, String especie, int edad, String dueño) {
        Nombre = nombre;
        Especie = especie;
        Edad = edad;
        Dueño = dueño;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEspecie() {
        return Especie;
    }

    public void setEspecie(String especie) {
        Especie = especie;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public String getDueño() {
        return Dueño;
    }

    public void setDueño(String dueño) {
        Dueño = dueño;
    }
}
