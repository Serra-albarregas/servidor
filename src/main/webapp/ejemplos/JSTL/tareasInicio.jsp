<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>Inicio</title></head>
<body>
    <h2>Gestor de Tareas</h2>
    <form action="${pageContext.request.contextPath}/tareasServlet" method="get">
        <label for="usuario">Usuario</label>
        <input type="text" id="usuario" name = "usuario">
        <input type="submit" value="entrar">
    </form>

</body>
</html>
