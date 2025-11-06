package com.serra.beans.ejemplos;

import java.io.Serializable;

public class Gato implements Serializable {
    private String nombre;
    private int edad;

    public Gato() {}

    public Gato(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int calcularEdadHumana() {
        return edad * 7;
    }
}
