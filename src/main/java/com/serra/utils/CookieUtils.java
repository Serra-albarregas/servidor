package com.serra.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pablo Collar Serradilla
 */
public class CookieUtils {

    /**
     * Obtiene una cookie de la solicitud HTTP por su nombre.
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * HttpServletRequest request = ...;
     * Cookie cookie = CookieUtils.getCookie(request, "miCookie");
     * if (cookie != null) {
     *     System.out.println("Valor de la cookie: " + cookie.getValue());
     * }
     * </pre>
     *
     * @param request la solicitud HTTP desde la cual obtener la cookie
     * @param nombre  el nombre de la cookie que se desea recuperar
     * @return la cookie correspondiente al nombre proporcionado, o {@code null} si no existe
     */
    public static Cookie getCookie(HttpServletRequest request, String nombre) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (nombre.equals(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Elimina una cookie de la solicitud HTTP.
     *
     * <p>La eliminación se realiza estableciendo la edad máxima de la cookie en 0 y
     * agregándola a la respuesta HTTP.</p>
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * HttpServletRequest request = ...;
     * HttpServletResponse response = ...;
     * boolean eliminado = CookieUtils.deleteCookie(request, response, "miCookie");
     * if (eliminado) {
     *     System.out.println("Cookie eliminada correctamente");
     * }
     * </pre>
     *
     * @param request  la solicitud HTTP que contiene la cookie
     * @param response la respuesta HTTP donde se aplicará la eliminación
     * @param nombre   el nombre de la cookie que se desea eliminar
     * @return {@code true} si se encontró y eliminó la cookie, {@code false} en caso contrario
     */
    public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String nombre) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (nombre.equals(c.getName())) {
                    c.setMaxAge(0);
                    response.addCookie(c);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Codifica un mapa en una cookie con un nombre específico.
     *
     * <p>Convierte cada clave y valor del mapa a String utilizando {@link #objToString(Object)}.
     * La cookie resultante tendrá el formato: "key1=value1&key2=value2".</p>
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * Map&lt;Persona, Integer&gt; personaMap = new HashMap&lt;&gt;();
     * personaMap.put(new Persona(1, "Juan"), 10);
     * personaMap.put(new Persona(2, "Ana"), 20);
     *
     * Cookie cookie = CookieUtils.encodeMapForCookie("personaCookie", personaMap);
     * System.out.println("Cookie creada: " + cookie.getName() + " = " + cookie.getValue());
     * </pre>
     *
     * @param cookieKey el nombre que se asignará a la cookie
     * @param map       el mapa a codificar
     * @param <T>       tipo de las claves del mapa
     * @param <V>       tipo de los valores del mapa
     * @return la cookie generada con el mapa codificado
     */
    public static <T,V> Cookie encodeMapForCookie(String cookieKey, Map<T, V> map) {
        String value = encodeMapForCookie(map);
        return new Cookie(cookieKey, value);
    }

    
    /**
     * Codifica un mapa en una cadena de texto con formato clave=valor.
     *
     * <p>Claves y valores nulos se omiten. Claves complejas se convierten en String
     * utilizando {@link #objToString(Object)}. Los pares se separan con '&'.</p>
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * Map&lt;String, Integer&gt; map = new HashMap&lt;&gt;();
     * map.put("uno", 1);
     * map.put("dos", 2);
     *
     * String encoded = CookieUtils.encodeMapForCookie(map);
     * System.out.println(encoded); // Salida: "uno=1&dos=2"
     * </pre>
     *
     * @param map el mapa a codificar
     * @param <T> tipo de las claves del mapa
     * @param <V> tipo de los valores del mapa
     * @return una cadena de texto con el mapa codificado
     */
    public static <T, V> String encodeMapForCookie(Map<T, V> map) {
        StringBuilder builder = new StringBuilder();
        for (Entry<T, V> entry : map.entrySet()) {
            T key = entry.getKey();
            V value = entry.getValue();
            if (key != null && value != null) {
                String keyStr = objToString(key);

                if (!builder.isEmpty()) {
                    builder.append("&");
                }
                builder.append(keyStr).append("=").append(value != null ? value.toString() : "null");
            }
        }

        return builder.toString();
    }

    /**
     * Decodifica una cookie y reconstruye un mapa usando una colección de objetos como referencia de claves.
     * <p>Los valores se mantienen como cadenas.</p>
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * Cookie cookie = ...; // Cookie con valor "1=10&2=20"
     * List&lt;Persona&gt; personas = Arrays.asList(new Persona(1, "Juan"), new Persona(2, "Ana"));
     * Map&lt;Persona, String&gt; map = CookieUtils.decodeCookie(cookie, personas);
     *
     * for (Map.Entry&lt;Persona, String&gt; entry : map.entrySet()) {
     *     System.out.println("Clave: " + entry.getKey().getNombre() + ", Valor: " + entry.getValue());
     * }
     * </pre>
     *
     * @param cookie     la cookie a decodificar
     * @param collection la colección de objetos posibles para las claves
     * @param <T>        tipo de las claves
     * @return un mapa con las claves de la colección y valores como cadenas
     */
    public static <T> Map<T, String> decodeCookie(Cookie cookie, Collection<T> collection) {
        return decodeCookie(cookie.getValue(), collection);
    }

    /**
     * Decodifica una cadena de texto con formato clave=valor y reconstruye un mapa usando una colección de objetos como referencia de claves.
     *
     * <p>Ejemplo de uso:</p>
     * <pre>
     * String cookieValue = "1=10&2=20";
     * List&lt;Persona&gt; personas = Arrays.asList(new Persona(1, "Juan"), new Persona(2, "Ana"));
     * Map&lt;Persona, String&gt; map = CookieUtils.decodeCookie(cookieValue, personas);
     *
     * for (Map.Entry&lt;Persona, String&gt; entry : map.entrySet()) {
     *     System.out.println("Clave: " + entry.getKey().getNombre() + ", Valor: " + entry.getValue());
     * }
     * </pre>
     *
     * @param cookieValue la cadena de texto de la cookie codificada
     * @param collection  la colección de objetos posibles para las claves
     * @param <T>         tipo de las claves
     * @return un mapa con las claves de la colección y valores como cadenas
     */
    public static <T> Map<T, String> decodeCookie(String cookieValue, Collection<T> collection) {
        Map<T, String> map = new HashMap<>();
        String[] splitedValues = cookieValue.split("&");

        Map<String, T> collectionMap = new HashMap<>();
        for (T obj : collection) {
            collectionMap.put(objToString(obj), obj);
        }

        for (String value : splitedValues) {
            String[] pair = value.split("=");
            if (collectionMap.containsKey(pair[0])) {
                map.put(collectionMap.get(pair[0]), pair[1]);
            }
        }

        return map;
    }

    
    /**
     * Convierte un objeto en una representación de cadena que puede usarse como clave en cookies.
     * <p>
     * <ul>
     *     <li>Tipos simples: String, Number, Boolean, Character → se utiliza {@code toString()}.</li>
     *     <li>Objetos complejos: se busca un método público sin parámetros llamado
     *         {@code getId}, {@code getIdentifier} o {@code getIdentificador} (no sensible a mayúsculas/minúsculas) y se usa su valor.</li>
     *     <li>Si no se encuentra un identificador, se usa {@code hashCode()}.</li>
     * </ul>
     * </p>
     *
     * @param obj el objeto a convertir
     * @return la representación en cadena del objeto
     */
    private static String objToString(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (isSimple(obj.getClass())) {
            return obj.toString();
        }

        String[] validMethods = { "getid", "getidentifier", "getidentificador" };
        Method getIdMethod = null;
        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getMethods()) {
            for (String methodName : validMethods) {
                if (method.getName().equalsIgnoreCase(methodName) && method.getParameterCount() == 0) {
                    getIdMethod = method;
                }
            }
        }

        if (getIdMethod != null) {
            try {
                Object idValue = getIdMethod.invoke(obj);
                return idValue != null ? idValue.toString() : String.valueOf(obj.hashCode());
            } catch (IllegalAccessException | InvocationTargetException e) {
                return String.valueOf(obj.hashCode());
            }
        }

        return String.valueOf(obj.hashCode());
    }

    /**
     * Comprueba si una clase es de tipo "simple" que puede convertirse directamente en cadena.
     * <p>
     * Se consideran simples:
     * <ul>
     *     <li>Primitivos</li>
     *     <li>String</li>
     *     <li>Integer, Double, Long, Short, Byte, Float</li>
     *     <li>Boolean</li>
     *     <li>Character</li>
     * </ul>
     * </p>
     *
     * @param clazz la clase a comprobar
     * @return {@code true} si es simple, {@code false} si es compleja
     */
    private static boolean isSimple(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }

        if (clazz == String.class || clazz == Integer.class || clazz == Double.class || clazz == Character.class
                || clazz == Boolean.class || clazz == Byte.class || clazz == Short.class || clazz == Long.class
                || clazz == Float.class) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        //=====PRUEBA 1======
        // --- Crear mapa de prueba ---
        Map<Object, Object> map = new HashMap<>();
        map.put(1, "uno"); // clave simple
        map.put("claveStr", 42); // clave simple
        map.put(new Persona(100, "Juan"), 10); // clave compleja, valor simple
        map.put(new Persona(200, "Ana"), new Persona(201, "Carlos")); // clave y valor complejos
        map.put(null, "nulo"); // clave nula
        map.put("valorNull", null); // valor nulo

        // --- Codificar mapa ---
        String encoded = encodeMapForCookie(map);
        System.out.println("Encoded cookie string:");
        System.out.println(encoded);

        // --- Preparar colección de objetos para decodificación ---
        List<Object> collection = Arrays.asList(
                1,
                "claveStr",
                new Persona(100, "Juan"),
                new Persona(200, "Ana"),
                null);

        // --- Decodificar cookie ---
        Map<Object, String> decodedMap = decodeCookie(encoded, collection);

        // --- Mostrar resultados ---
        System.out.println("\nDecoded map:");
        for (Map.Entry<Object, String> entry : decodedMap.entrySet()) {
            System.out.println("Clave: " + entry.getKey() + " ("
                    + (entry.getKey() != null ? entry.getKey().getClass().getSimpleName() : "null") + ")"
                    + ", Valor: " + entry.getValue() + " ("
                    + (entry.getValue() != null ? entry.getValue().getClass().getSimpleName() : "null") + ")");
        }

        //=====PRUEBA 2======
        // --- Prueba HashMap<Persona, Integer> ---
        Map<Persona, Integer> personaMap = new HashMap<>();
        personaMap.put(new Persona(1, "Juan"), 10);
        personaMap.put(new Persona(2, "Ana"), 20);
        personaMap.put(new Persona(3, "Carlos"), 30);

        // Codificar el mapa
        String encodedPersonaStr = encodeMapForCookie(personaMap);
        System.out.println("\nEncoded Persona map:");
        System.out.println(encodedPersonaStr);

        // Crear colección de claves Persona para decodificación
        List<Persona> personaCollection = new ArrayList<>(personaMap.keySet());

        // Decodificar usando la colección
        Map<Persona, String> decodedPersonaMap = decodeCookie(encodedPersonaStr, personaCollection);

        // Mostrar resultados
        System.out.println("\nDecoded Persona map:");
        for (Map.Entry<Persona, String> entry : decodedPersonaMap.entrySet()) {
            Persona key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Clave: " + key + " (id=" + key.getId() + ")"
                    + ", Valor: " + value + " (" + (value != null ? value.getClass().getSimpleName() : "null") + ")");
        }
    }

    static class Persona {
        private final int id;
        private final String nombre;

        public Persona(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
}