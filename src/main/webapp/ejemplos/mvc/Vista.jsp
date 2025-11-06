<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.serra.beans.ejemplos.Gato" %>


<html>
<head>
    <title>Ejemplo MVC con Gato</title>
</head>
<body>
<h2>Formulario del Gato</h2>

<form action="<%=request.getContextPath() + "/ControladorServlet"%>" method="post">
    Nombre: <input type="text" name="nombre" /><br/>
    Edad: <input type="text" name="edad" /><br/>
    <input type="submit" value="Enviar" />
</form>

<hr/>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }
    Gato gato = (Gato) request.getAttribute("gato");
    Integer edadHumana = (Integer) request.getAttribute("edadHumana");
    String enSesion = (String) request.getAttribute("enSesion");

    if (gato != null) {
%>
    <h3>Datos del Gato</h3>
    <p>Nombre: <%= gato.getNombre() %></p>
    <p>Edad: <%= gato.getEdad() %> años</p>
    <p>Edad equivalente humana: <%= edadHumana %> años</p>
    <p><%= enSesion %></p>  
<%
    }
    java.util.Enumeration<String> nombres = session.getAttributeNames();
%>
    <h3>Gatos existentes en sesión</h3>
<%
    while (nombres.hasMoreElements()) {
        String clave = nombres.nextElement();
        Object object = session.getAttribute(clave);
        if (object instanceof Gato) {
            Gato gatoSesion = (Gato) object;
%>
            <p>Nombre: <%= gatoSesion.getNombre() %></p>
            <p>Edad: <%= gatoSesion.getEdad() %> años</p>
            <p>Edad equivalente humana: <%= gatoSesion.calcularEdadHumana() %> años</p>
<%
        }
    }

%>
</body>
</html>
