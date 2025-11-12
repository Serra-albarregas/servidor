package com.serra.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

public class Mastermind {
    private int nDigitos;
    private String solucion;
    private LinkedHashMap<String, int[]> intentos;

    public Mastermind(){

    }

    public Mastermind(int nDigitos){
        this.nDigitos = nDigitos;
        solucion = generarSolucion(nDigitos);
        intentos = new LinkedHashMap<>();
    }

    public int getNDigitos() {
        return this.nDigitos;
    }

    public void setNDigitos(int nDigitos) {
        this.nDigitos = nDigitos;
    }

    public String getSolucion() {
        return this.solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public LinkedHashMap<String,int[]> getIntentos() {
        return this.intentos;
    }

    public void setIntentos(LinkedHashMap<String,int[]> intentos) {
        this.intentos = intentos;
    }

    private String generarSolucion(int nDigitos){
        String solucion = "";
        ArrayList<Integer> posibilidades = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
        Random r = new Random();
        for (int i = 0; i < nDigitos; i++) {
            solucion+=posibilidades.remove(r.nextInt(posibilidades.size()));
        }

        return solucion;
    }

    private int[] comprobarIntento(String intento){
        int heridos = 0;
        int muertos = 0;
        for (int i = 0; i < intento.length(); i++) {
            if (intento.charAt(i)==solucion.charAt(i)) {
                muertos++;
            } else if (solucion.contains(String.valueOf(intento.charAt(i)))) {
                heridos++;
            }
        }
        int[] comprobacion = {heridos, muertos};
        return comprobacion;
    }

    public boolean intentoValido(String intento){
        boolean repetidos = false;
        for (int i = 0; i < intento.length() && !repetidos; i++) {
            int veces = 0;
            for (int j = 0; j < intento.length(); j++) {
                if (intento.charAt(i)==intento.charAt(j)){
                    veces++;
                }
            }
            if (veces>1) repetidos = true;
        }

        return intento.matches("[0-9]{"+nDigitos+"}") && !repetidos;
    }

    public boolean nuevoTurno(String intento){
        int [] comprobacion = comprobarIntento(intento);
        intentos.put(intento, comprobacion);
        boolean ganador = false;
        if (comprobacion[1] == nDigitos) {
            ganador = true;
        }

        return ganador;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Entry<String, int[]> intento : intentos.entrySet()) {
            builder.append(intento.getKey() + " " + intento.getValue()[0] + " " + intento.getValue()[1] + "\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Mastermind mm = new Mastermind(3);
        String intento = "";
        do {
            System.out.println(mm.toString());
            System.out.println("Adivina");
            do {
                intento = sc.nextLine();
            } while (!mm.intentoValido(intento));
        } while (!mm.nuevoTurno(intento));

        System.out.println("Has ganado");
    }
}
