package com.serra.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JSTLfn {
    public static ArrayList<String> coloresAleatorios(int cuantos) {
        ArrayList<String> colores = new ArrayList<>(Arrays.asList(
            "#264653", // Azul petróleo oscuro
            "#2A9D8F", // Verde azulado
            "#E9C46A", // Amarillo mostaza
            "#F4A261", // Naranja suave
            "#E76F51", // Coral
            "#8AB17D", // Verde suave
            "#577590", // Azul grisáceo
            "#B56576", // Rosa apagado
            "#6D597A", // Violeta grisáceo
            "#355070", // Azul medianoche
            "#E9AFA3", // Rosa pálido
            "#F6BD60", // Amarillo pastel
            "#84A59D", // Verde grisáceo
            "#F28482", // Rosa salmón
            "#9A8C98", // Malva suave
            "#CDB4DB", // Lila claro
            "#FFC8DD", // Rosa pastel
            "#FFAFCC", // Rosa fuerte
            "#BDE0FE", // Azul cielo pastel
            "#A2D2FF"  // Azul bebé
        ));
        
        Collections.shuffle(colores);

        return new ArrayList<>(colores.subList(0, cuantos));
    }
}
