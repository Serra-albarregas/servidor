<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false"%>

<%!
    public String limpiar(String s) {
        if (s == null) return "";
        return s.replace(" ", "_")
                .replaceAll("[()<>@,;:\\/\"?\\[\\]={}]", "");

    }
    public String escapar(String s) {
      if (s == null) return "";
      return s.replace("&", "&amp;")
              .replace("<", "&lt;")
              .replace(">", "&gt;")
              .replace("\"", "&quot;")
              .replace("'", "&#x27;");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de cookies</title>
</head>
<body>
    <%-- FORMULARIO --%>
<%
    String accion = request.getParameter("accion");
    String actualizar = request.getParameter("actualizar");
    String nombre = request.getParameter("nombre")!=null?request.getParameter("nombre"):"";
    String valor = request.getParameter("valor")!=null?request.getParameter("valor"):"";
    String mensaje = request.getParameter("mensaje")!=null?request.getParameter("mensaje"):"";
%>
    <form action="gestionCookiesCompleto.jsp" method="get">
        <input type="hidden" name="actualizar" value="true">
        <input type="hidden" name="accion" value="<%= escapar(accion) %>">
        <input type="text" name="nombre" id="nombre" <%="modificar".equals(accion)?"readonly":""%> value="<%= "modificar".equals(accion) ? escapar(nombre) : "" %>">
        <input type="text" name="valor" id="valor" value="<%= "modificar".equals(accion) ? escapar(valor) : "" %>">
        <input type="submit" value="<%="modificar".equals(accion)?"Modificar cookie":"Crear cookie"%>">
    </form>

    <%-- MENSAJE --%>

<%
    if (!"".equals(mensaje)) {
%>
        <p><%=mensaje%></p>
<%
    }
%>
    <p></p>

    <%-- LISTA --%>

    <table>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies!=null) {
        for(Cookie c : cookies) {
%>
            <tr>
            <td><%= escapar(c.getName()) %></td>
            <td><%= escapar(c.getValue()) %></td>
            <td><form action="gestionCookiesCompleto.jsp" method="get">
                <input type="hidden" name="accion" value="modificar">
                <input type="hidden" name="nombre" value="<%= escapar(c.getName()) %>">
                <input type="hidden" name="valor" value="<%= escapar(c.getValue()) %>">
                <input type="submit" value="modificar">
            </form></td>
            <td><form action="gestionCookiesCompleto.jsp" method="get">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="actualizar" value="true">
                <input type="hidden" name="nombre" value="<%= escapar(c.getName()) %>">
                <input type="hidden" name="valor" value="<%= escapar(c.getValue()) %>">
                <input type="submit" value="eliminar">
            </form></td>
            </tr>
<%
        }
    }
%>
    </table>

    <%-- CONFIRMACIÓN --%>

<%
    if ("true".equals(actualizar)) {
        String mensjeEnv = "";
        if (nombre.equals("") || valor.equals("")) {
            mensjeEnv = "Error - los campos no deben estar vacíos";
        } else {
            Cookie cookie = new Cookie(limpiar(nombre), limpiar(valor));
            if ("eliminar".equals(accion)) {
                cookie.setMaxAge(0);
            }
            response.addCookie(cookie);
            if ("eliminar".equals(accion)) {
                mensjeEnv = "Cookie " + escapar(nombre) + " eliminada";
            }else if ("modificar".equals(accion)){
                mensjeEnv = "Cookie " + escapar(nombre) + " modificada con el valor " + escapar(valor);
            }else{
                mensjeEnv = "Cookie " + escapar(nombre) + " creada con el valor " + escapar(valor);
            }
        }
        response.sendRedirect("gestionCookiesCompleto.jsp?mensaje=" + mensjeEnv);
    }
%>
</body>
</html>