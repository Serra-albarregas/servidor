<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Obtener sesión</title>
</head>
<body>
<%
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date creationDate = new Date(session.getCreationTime());
    String formattedCreationTime = formatter.format(creationDate);
    if (session.getAttribute("usuario")!=null){
%>
    <p>Id <%=session.getId()%> Usuario <%=session.getAttribute("usuario")%> Fecha de Creación <%=formattedCreationTime%></p>
<%
    }else{
%>
    <p>No se ha creado una sesión todavía</p>
<%
    }
%>
</body>
</html>

