package com.serra.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class Usuario implements Serializable {
    private String id;
    private String nombre;
    private LocalDate nacimiento;
    private int hijos;
    private float salario;

    public Usuario() {
    }

    public Usuario(String id, String nombre, LocalDate nacimiento, int hijos, float salario) {
        this.id = id;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.hijos = hijos;
        this.salario = salario;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getNacimiento() {
        return this.nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getHijos() {
        return this.hijos;
    }

    public void setHijos(int hijos) {
        this.hijos = hijos;
    }

    public float getSalario() {
        return this.salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
}
