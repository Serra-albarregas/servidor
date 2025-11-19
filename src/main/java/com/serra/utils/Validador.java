package com.serra.utils;

import javax.servlet.http.HttpServletRequest;

public class Validador {
    public static boolean validarNull(Object... objects) {
        boolean valido = true;
        for (Object object : objects) {
            if (object == null) {
                valido = false;
            }
        }
        return valido;
    }

    public static boolean validarVacio(String... strings) {
        boolean valido = true;
        for (String string : strings) {
            if ("".equals(string)) {
                valido = false;
            }
        }
        return valido;
    }

    public static Integer validarEntero(String numeroStr, int min, int max) throws IllegalArgumentException {

        if (numeroStr == null || numeroStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo no debe estar vacío");
        }

        try {
            int numero = Integer.parseInt(numeroStr.trim());
            if (numero < min || numero > max) {
                throw new IllegalArgumentException(
                        "El valor de " + numeroStr + " debe estar comprendido entre " + min + " y " + max);
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error de formato en el campo " + numeroStr);
        }
    }

    public static Integer validarEntero(String numeroStr, int nDigitos) throws IllegalArgumentException {

        if (numeroStr == null || numeroStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo no debe estar vacío");
        }

        if (numeroStr.length() != nDigitos) {
            throw new IllegalArgumentException("El número de dígitos debe ser " + nDigitos);
        }

        try {
            int numero = Integer.parseInt(numeroStr.trim());
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error de formato en el campo " + numeroStr);
        }
    }

    public static Integer validarEntero(String numeroStr){
        if (numeroStr == null || numeroStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo no debe estar vacío");
        }
        try {
            int numero = Integer.parseInt(numeroStr.trim());
            return numero;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error de formato en el campo " + numeroStr);
        }
    }

    public static String validarTamanioStr(String numeroStr, int nDigitos) throws IllegalArgumentException {

        if (numeroStr == null || numeroStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo no debe estar vacío");
        }

        if (numeroStr.length() != nDigitos) {
            throw new IllegalArgumentException("El número de dígitos debe ser " + nDigitos);
        }

        return numeroStr;
    }

    public static String validarString(String string) {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo no debe estar vacío");
        }

        return string;
    }
}
