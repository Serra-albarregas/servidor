# JSP Avanzado: JSTL y EL

## 1. ¿Que son JSTL y EL?

EL (Expression Language) es un lenguaje de expresiones simple que permite acceder y mostrar datos en una página JSP sin escribir Java.

Sirve para:
- Acceder a atributos del request, session, application o page.
- Leer parámetros del formulario, cookies, cabeceras, etc.
- Evaluar expresiones lógicas y aritméticas.
- Navegar por objetos JavaBean.

JSTL es una colección de etiquetas que amplían las capacidades de JSP.

Ofrecen estructuras de control (`if`, `forEach`), manipulación de variables, acceso a BD, internacionalización y manejo de XML.

JSTL está compuesta por **cinco librerías** principales:

| Librería | Prefijo | URI | Propósito |
|------------|-----------------|-------------------|---------------|
|Core | c |http://java.sun.com/jsp/jstl/core | Variables, control de flujo, URLs|
XML | x | http://java.sun.com/jsp/jstl/xml | Procesar documentos XML
I18N | fmt | http://java.sun.com/jsp/jstl/fmt | Internacionalización (idiomas, formatos) |
Database | sql | http://java.sun.com/jsp/jstl/sql | Acceso básico a bases de datos |
Functions | fn | http://java.sun.com/jsp/jstl/functions | Funciones de cadenas |

## Expression Language (EL)

### Sintaxis

Las expresiones EL se escriben entre `${ ... }` y permiten acceder a atributos y métodos de los beans a través de sus getters y setters.

``` jsp
${usuario.nombre}
${producto.precio}
${sessionScope.usuario.rol}
```

### Objetos implícitos

EL cuenta con una serie de objetos implícitos donde se pueden buscar otros objetos. Estos objetos implíticos están relacionados con diferentes **ámbitos** de la aplicación.

| Objeto | Descripción |
|--------|------------|
| `pageScope` | Atributos del ámbito de página |
| `requestScope` | Atributos del ámbito de la petición (`request.getAttribute()`) |
| `sessiónScope` | Atributos de sesión |
| `applictionScope` | Atributos globales |
| `param` | Parámetros de la petición (`request.getParameter("nombre")`) |
| `paramValues` | Parámetros múltiples |
| `header` | Cabeceras HTTP |
| `headerValues` | Cabeceras múltiples |
| `cookie` | Cookies disponibles |
| `initParam` | Parámetros del contexto (web.xml) |
| `pageContext` | Acceso directo al contexto de la página |


Ejemplo
``` jsp
${param.usuario}         <!-- Parámetro GET/POST -->
${cookie.idSesion.value} <!-- Cookie -->
${header["User-Agent"]}  <!-- Cabecera -->
```

Cuando escribes `${variable}`, EL busca la variable en este orden:
1. `pageScope`: varibles en la página actual.
2. `requestScope`: atributos de la petición.
3. `sessionScope`: atributos de la sesión de usuario.
4. `applicationScope`: atributos globales de la aplicación.

Imaginemos que tenemos la clase Persona y la clase Perro:

``` java
public class Persona(){
    private Perro perro;
    //Constructor, getters y setters
}

public class Perro(){
    private String nombre;
    //Constructor, getters y setters
}
```

Como nuestras clases cumplen la especificación de JavaBean (constructor por defecto, getters y setters para todos los atributos e implementación de la interfaz `Serializable`) podemos acceder a los atributos de la siguiente manera usando EL.

``` jsp
${persona.perro.nombre}
```

Que sería equivalente a:

``` java
persona.getPerro.getNombre();
```

Otro ejemplo, en este caso comparamos el acceso al valor de una Cookie. En Java se realizaría de la siguente manera:

``` java
<%
Cookie[] cookies = request.getCookies();
for(int i=0; i<cookies.lenght; i++){
    if((cookies[i].getName()).equals(“nombreUsuario”)){
        %>
        <%= cookies[i].getValue() %>
        <%
    }
}
%>
```

Mientas que utilizando EL se simplifica:
``` jsp
${cookie.nombreUsuario.value}
```

Las expresiones EL también admiten en uso de corchetes `[]`. Se pueden utilizar en dos situaciones:
- Como sustituto del `.` en el acceso a objetos. `${perro.nombre}` es equivalente a `${perro["nombre]}`.
- Para acceder a elementos de tipo colección (como arrays, Listas o Mapas), por ejemplo, si tuviesemos una lista de objetos de tipo Perro, podríamos acceder a uno de ellos de la siguiente manera `${perros[3].nombre}`.

## JSP Standar Tag Library (JSTL)

Para usar JSTL, añade la directiva correspondiente:

``` jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
```

Donde se selecccionará la uri y el prefijo dependiendo de la librería que se vaya a utilizar.

### Librería cores `c`

Proporciona etiquetas para el control de flujo, variables, URLs, etc.

#### `c:set`: definir variables

Se crea una variable en el ámbito seleccionado.

``` jsp
<c:set var="nombre" value="Carlos" scope="session" />
```

#### `c:remove`: eliminar variables

Borra una variable del ambito seleccionado.

``` jsp
<c:remove var="nombre" scope="session" />
```

#### `c:if`: condición simple

Estructura de control de tipo if, donde si se cumple la condición se muestra el contenido entre las etiquetas `<c:if>`

``` jsp
<c:if test="${usuario.activo}">
  <p>Usuario activo</p>
</c:if>
```

#### `c:choose`,`c:when`,`c:otherwise`: switch

Estructura de control de tipo switch. Donde `c:choose`=`switch`, `c:when`=`case` y `c:otherwise`=`default`.

``` jsp
<c:choose>
  <c:when test="${edad < 18}">Menor</c:when>
  <c:when test="${edad < 65}">Adulto</c:when>
  <c:otherwise>Jubilado</c:otherwise>
</c:choose>
```

#### `c:forEach`: foreach

Estructura de control de tipo foreach para recorrer colecciones.

```jsp
<c:forEach var="libro" items="${libros}">
  <li>${libro.titulo}</li>
</c:forEach>
```

```jsp
<c:forEach var="i" begin="1" end="5" step="1">
  ${i}
</c:forEach>
```

#### `c:forTokens`: foreach

Estructura de control de tipo foreach donde tenemos un grupo de elementos en una cadena de texto.

```jsp
<c:forTokens var="color" items="rojo,verde,azul" delims=",">
  ${color}<br>
</c:forTokens>
```

#### `c:import`: incluir contenido

Importa el contenido de un recurso de Internet en una variable EL. Puede
utilizar param para especificar parámetros.

``` jsp
<c:import url="/introduccion.txt" var="texto" />
```

#### `c:redirect`: redirigir

Envía al cliente un código HTTP de redirección. Puede utilizar param para
especificar parámetros.

``` jsp
<c:redirect url="login.jsp" />
```

#### `c:catch`: try-catch

Captura una excepción. La excepción no se propaga y por tanto no se
invocará la página JSP de error si la hubiera.

``` jsp
<c:catch var="error">
  <c:import url="http://servidor_inexistente.com" />
</c:catch>

<c:if test="${not empty error}">
  Error: ${error.message}
</c:if>
```

#### `c:out`: Imprimir expresiones

Evalúa una expresión EL y devuelve su valor.

``` jsp
<c:out value="${usuario.nombre}" default="Invitado" />
```

### Librería Functions (`fn`)

Son funciones que pueden utilizarse dentro de EL (no son etiquetas, son funciones auxiliares).

``` jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
```

| Función | Descripción | Ejemplo |
|----------|-------------|----------|
| `fn:length(x)` | Longitud | `${fn:length(lista)}` |
| `fn:toUpperCase(x)` | Mayúsculas | `${fn:toUpperCase(nombre)}` |
| `fn:toLowerCase(x)` | Minúsculas | `${fn:toLowerCase(nombre)}` |
| `fn:trim(x)` | Quita espacios | `${fn:trim(nombre)}` |
| `fn:substring(x, i, j)` | Subcadena | `${fn:substring(nombre,0,3)}` |
| `fn:replace(x,a,b)` | Reemplaza texto | `${fn:replace(texto,'a','@')}` |
| `fn:contains(x,y)` | Contiene | `${fn:contains(cadena,'abc')}` |
| `fn:startsWith(x,y)` | Empieza con | `${fn:startsWith(cadena,'http')}` |
| `fn:endsWith(x,y)` | Termina con | `${fn:endsWith(cadena,'.jpg')}` |
| `fn:split(x, sep)` | Divide cadena | `${fn:split(lista,',')}` |
| `fn:join(list, sep)` | Une cadenas | `${fn:join(array,'-')}` |
| `fn:escapeXml(x)` | Escapa XML | `${fn:escapeXml(texto)}` |