package com.serra;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        /*System.out.println(Arrays.toString(calcularCambio(1)));
        System.out.println(Arrays.toString(calcularCambio(1.5f)));
        System.out.println(Arrays.toString(calcularCambio(0.65f)));
        System.out.println(Arrays.toString(calcularCambio(7.3f)));
        System.out.println(Arrays.toString(calcularCambio(126.32f)));
        System.out.println(Arrays.toString(calcularCambio(93.5f)));
        System.out.println(Arrays.toString(calcularCambio(0f)));

        System.out.println(calcularDivisa(5, "EUR", "JPY"));
        System.out.println(calcularDivisa(5, "AUD", "SEK"));
        System.out.println(calcularDivisa(5, "CHF", "USD"));
        System.out.println(calcularDivisa(5, "NZD", "NZD"));
        System.out.println(calcularDivisa(5, "CAD", "JPY"));*/


        try {
            String codificado = URLEncoder.encode("HOLA BUENDIA_-;:", "UTF-8");
            System.out.println(URLEncoder.encode(codificado, "UTF-8"));
            System.out.println(URLDecoder.decode(codificado, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int[] calcularCambio(float input) {
        int [] valores = {1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000};
        int [] cambio = new int[valores.length];
        int resto = (int) Math.floor(input*100);
        int i = valores.length-1;
        while (resto>0){
            if (resto>=valores[i]){
                cambio[i]++;
                resto-=valores[i];
            } else {
                i--;
            }
        }
        return cambio;
    }

    public static double calcularDivisa(double desde, String divDesde, String divHasta) throws IllegalArgumentException{
        HashMap<String, Double> tasas = new HashMap<>();
        tasas.put("USD", 1.0);
        tasas.put("EUR", 0.86);
        tasas.put("GBP", 0.81);
        tasas.put("JPY", 152.0);
        tasas.put("AUD", 1.51);
        tasas.put("CAD", 1.35);
        tasas.put("CHF", 0.91);
        tasas.put("CNY", 7.2);
        tasas.put("SEK", 10.3);
        tasas.put("NZD", 1.59);


        if (!tasas.containsKey(divDesde) || !tasas.containsKey(divHasta)){
            throw new IllegalArgumentException("Divisa no válida");
        }
        return desde * tasas.get(divHasta) / tasas.get(divDesde);
    }
}