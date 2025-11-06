<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.serra.beans.Usuario" %>

<%!
    String escapeHtml(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;");
    }
%>
<%
    String estado = request.getParameter("estado") != null ? request.getParameter("estado") : "";
    String id = request.getParameter("id") != null ? request.getParameter("id") : "";
    Usuario usuario = !"".equals(id) ? (Usuario) session.getAttribute(id) : null;

    String nombre = "";
    String nacimiento = "";
    String hijos = "";
    String salario = "";

    if (("modificar".equals(estado) || "eliminar".equals(estado)) && usuario != null) {
        nombre = usuario.getNombre();
        hijos = String.valueOf(usuario.getHijos());
        salario = String.valueOf(usuario.getSalario());
        nacimiento = usuario.getNacimiento() != null ? usuario.getNacimiento().toString() : "";
    }

    String mensajeAviso = (String) session.getAttribute("mensajeAviso");
    if (mensajeAviso != null) session.removeAttribute("mensajeAviso");

    String mensajeErr = (String) session.getAttribute("mensajeErr");
    if (mensajeErr != null) session.removeAttribute("mensajeErr");

    String mensajeConfirmacion = (String) session.getAttribute("mensajeConfirmacion");
    if (mensajeConfirmacion != null) session.removeAttribute("mensajeConfirmacion");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestión de sesión</title>
</head>
<body>

<form action="controladorSesion.jsp" method="post">
    <fieldset>
        <legend>Buscar</legend>
        <label for="id">Nombre</label>
        <input type="text" id="id" name="id"
            <% if ("crear".equals(estado) || "modificar".equals(estado) || "eliminar".equals(estado)){ %>
                readonly value="<%= escapeHtml(id) %>"
            <% } %>
        >
        <input type="submit" name="accion" value="Buscar">
    </fieldset>

    <% if (!"".equals(estado)) { %>
    <fieldset>
        <legend>Datos del usuario</legend>
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" value="<%= escapeHtml(nombre) %>">
        <label for="nacimiento">Fecha de nacimiento</label>
        <input type="date" id="nacimiento" name="nacimiento" value="<%= escapeHtml(nacimiento) %>">
        <label for="hijos">Número de hijos</label>
        <input type="number" id="hijos" name="hijos" value="<%= escapeHtml(hijos) %>">
        <label for="salario">Salario</label>
        <input type="number" id="salario" name="salario" value="<%= escapeHtml(salario) %>">
    </fieldset>
    <% } %>

    <fieldset>
        <legend>Acciones</legend>

        <% if (mensajeConfirmacion != null) { %>
            <p style="color:blue;"><%= escapeHtml(mensajeConfirmacion) %></p>
        <% } %>

        <input type="submit" formaction="vistaSesion.jsp" value="Inicio">

        <% if ("crear".equals(estado)) { %>
            <input type="submit" name="accion" value="Crear">
        <% } else if ("modificar".equals(estado)) { %>
            <input type="submit" name="accion" value="Modificar">
            <input type="submit" name="accion" value="Eliminar">
        <% } else if ("eliminar".equals(estado)) { %>
            <input type="submit" name="accion" value="Conforme">
            <input type="submit" name="accion" value="Cancelar">
        <% } %>
    </fieldset>
</form>

<% if (mensajeAviso != null) { %>
    <p style="color:green;"><%= escapeHtml(mensajeAviso) %></p>
<% } %>

<% if (mensajeErr != null) { %>
    <p style="color:red;"><%= escapeHtml(mensajeErr) %></p>
<% } %>

</body>
</html>
