package com.serra.utils;

public class Validador {
    public static boolean validarNull(Object... objects) {
        boolean valido = true;
        for (Object object : objects) {
            if (object==null){
                valido = false;
            }
        }
        return valido;
    }

    public static boolean validarVacio(String... strings) {
        boolean valido = true;
        for (String string : strings) {
            if ("".equals(string)){
                valido = false;
            }
        }
        return valido;
    }
}
