# Etiquetas JSP y Objetos Implícitos

En las páginas **JSP (JavaServer Pages)**, se combinan **HTML** y **código Java** para generar contenido dinámico.  
JSP ofrece **etiquetas especiales** y **objetos implícitos** que simplifican el trabajo con Servlets y datos del servidor.

---

## 1. Tipos de etiquetas JSP

Las etiquetas JSP permiten insertar código Java o directivas dentro del HTML.  
Se dividen en varios tipos principales:

| Tipo de etiqueta | Sintaxis | Descripción |
|------------------|-----------|--------------|
| **Directiva** | `<%@ ... %>` | Configura aspectos de la página JSP, como importaciones o codificación. |
| **Scriptlet** | `<% ... %>` | Contiene código Java que se ejecuta al procesar la página. |
| **Expresión** | `<%= ... %>` | Evalúa una expresión Java y muestra su resultado en el HTML. |
| **Declaración** | `<%! ... %>` | Declara variables o métodos que estarán disponibles en toda la página. |
| **Comentarios JSP** | `<%-- ... --%>` | Comentarios que no se envían al cliente (a diferencia de los HTML). |
| **Acciones JSP** | `<jsp:...>` | Llaman a funcionalidades predefinidas de JSP, como incluir páginas o reenviar peticiones. |

---

### 1.1 Directivas JSP (`<%@ ... %>`)

Las directivas **configuran el entorno** de la página JSP.  
Las más comunes son:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, java.io.*" %>
<%@ include file="header.jsp" %>
```

**Principales directivas:**
- `page`: define propiedades de la página (importaciones, buffers, errores, etc.).  
- `include`: inserta contenido de otro archivo JSP en tiempo de compilación.

---

### 1.2 Scriptlets (`<% ... %>`)

Permiten insertar **código Java** directamente dentro de la página.

```jsp
<%
    int contador = 5;
    out.println("El valor del contador es: " + contador);
%>
```

---

### 1.3 Expresiones (`<%= ... %>`)

Evalúan una expresión y muestran el resultado en el HTML.

```jsp
<p>La hora actual es: <%= new java.util.Date() %></p>
```

Equivale a escribir:
```jsp
<% out.print(new java.util.Date()); %>
```

---

### 1.4 Declaraciones (`<%! ... %>`)

Permiten **definir variables o métodos** que pertenecen a la clase generada del JSP.

```jsp
<%!
    int contador = 0;
    public int incrementar() { 
        return ++contador; 
    }
%>
<p>Contador: <%= incrementar() %></p>
```

---

### 1.5 Comentarios JSP (`<%-- ... --%>`)

Comentarios que **no se envían al navegador**, útiles para desarrolladores:

```jsp
<%-- Este comentario no será visible en el HTML generado --%>
```

---

### 1.6 Acciones JSP (`<jsp:...>`)

Usan una sintaxis XML y realizan tareas comunes dentro de JSP.

| Acción | Descripción | Ejemplo |
|--------|--------------|----------|
| `<jsp:include>` | Incluye otra página JSP en tiempo de ejecución. | `<jsp:include page="menu.jsp" />` |
| `<jsp:forward>` | Redirige la petición a otro recurso. | `<jsp:forward page="login.jsp" />` |
| `<jsp:param>` | Envía parámetros a otra página. | `<jsp:include page="pagina.jsp"><jsp:param name="id" value="123"/></jsp:include>` |
| `<jsp:useBean>` | Crea o accede a un JavaBean. | `<jsp:useBean id="usuario" class="com.app.Usuario" />` |
| `<jsp:setProperty>` | Asigna valores a propiedades del bean. | `<jsp:setProperty name="usuario" property="nombre" value="Ana"/>` |
| `<jsp:getProperty>` | Obtiene y muestra el valor de una propiedad. | `<jsp:getProperty name="usuario" property="nombre"/>` |

---

## 2. Objetos implícitos de JSP

JSP proporciona varios **objetos predefinidos** disponibles automáticamente en la página, sin necesidad de declararlos.

| Objeto | Tipo | Descripción |
|--------|------|--------------|
| `request` | `HttpServletRequest` | Contiene datos de la petición HTTP. |
| `response` | `HttpServletResponse` | Permite modificar la respuesta enviada al cliente. |
| `session` | `HttpSession` | Gestiona datos de usuario durante su sesión. |
| `application` | `ServletContext` | Información compartida entre todas las sesiones. |
| `out` | `JspWriter` | Permite escribir contenido en la salida HTML. |
| `pageContext` | `PageContext` | Proporciona acceso a todos los objetos JSP y al ámbito de la página. |
| `config` | `ServletConfig` | Configuración del servlet asociado a la página JSP. |
| `page` | `Object` | Referencia a la instancia actual de la página JSP (similar a `this`). |
| `exception` | `Throwable` | Solo disponible en páginas de error (`isErrorPage="true"`). |

---

### Ejemplo de uso de objetos implícitos

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head><title>Ejemplo JSP</title></head>
<body>
    <h1>Bienvenido, <%= request.getParameter("usuario") %></h1>
    <p>Tu sesión ID es: <%= session.getId() %></p>
    <p>Servidor: <%= application.getServerInfo() %></p>
</body>
</html>
```

---

## Buenas prácticas modernas

- Evita usar **scriptlets** (`<% %>`).  
  Usa **Expression Language (EL)** y **JSTL** en su lugar.  
- Separa la lógica de negocio del JSP usando **Servlets** o **patrones MVC/MVVM**.  
- Configura correctamente la codificación y evita concatenar HTML desde Java.

---

 **Conclusión:**  
Las etiquetas JSP y los objetos implícitos son herramientas poderosas para generar contenido dinámico. Sin embargo, en aplicaciones modernas, se recomienda usarlas con cuidado y apoyarse en **JSTL** y **EL** para mantener el código limpio y mantenible.
