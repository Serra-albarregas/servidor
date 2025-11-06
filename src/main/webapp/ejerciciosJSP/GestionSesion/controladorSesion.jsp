<%@ page import="com.serra.beans.Usuario, com.serra.utils.Validador" %>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter" %>

<%
    String accion = request.getParameter("accion");
    String id = request.getParameter("id");
    String nombre = request.getParameter("nombre");
    String nacimientoStr = request.getParameter("nacimiento");
    String hijosStr = request.getParameter("hijos");
    String salarioStr = request.getParameter("salario");

    String estado = "";
    Usuario usuario = null;

    String mensajeAviso = null;
    String mensajeErr = null;
    String mensajeConfirmacion = null;

    switch (accion != null ? accion : "") {
        case "Buscar":
            if (id == null || id.isEmpty()) {
                mensajeErr = "El campo no puede estar vacío";
            } else {
                usuario = (Usuario) session.getAttribute(id);
                if (usuario != null) {
                    estado = "modificar";
                } else {
                    estado = "crear";
                }
            }
            break;

        case "Crear":
        case "Modificar":
            if (Validador.validarNull(id, nombre, nacimientoStr, hijosStr, salarioStr) &&
                Validador.validarVacio(id, nombre, nacimientoStr, hijosStr, salarioStr)) {
                try {
                    LocalDate nacimiento = LocalDate.parse(nacimientoStr, DateTimeFormatter.ISO_LOCAL_DATE);
                    int hijos = Integer.parseInt(hijosStr);
                    float salario = Float.parseFloat(salarioStr);
                    usuario = new Usuario(id, nombre, nacimiento, hijos, salario);
                    session.setAttribute(id, usuario);
                    mensajeAviso = "Usuario " + id + (accion.equals("Crear") ? " creado" : " modificado") + " con éxito";
                } catch (Exception e) {
                    estado = accion.toLowerCase();
                    mensajeErr = "Error en el formato de los campos";
                }
            } else {
                estado = accion.toLowerCase();
                mensajeErr = "Error, los campos no deben estar vacíos";
            }
            break;

        case "Eliminar":
            estado = "eliminar";
            mensajeConfirmacion = "¿Seguro que quieres eliminar?";
            break;

        case "Conforme":
            usuario = (Usuario) session.getAttribute(id);
            if (usuario != null) {
                session.removeAttribute(id);
                mensajeAviso = "Usuario eliminado con éxito";
            } else {
                mensajeErr = "No se puede eliminar";
            }
            break;

        case "Cancelar":
            mensajeAviso = "Operación cancelada";
            if (id != null && !id.isEmpty()) {
                estado = "modificar";
            }
            break;

        default:
            mensajeErr = "Ha ocurrido un error";
    }

    if (mensajeAviso != null) session.setAttribute("mensajeAviso", mensajeAviso);
    if (mensajeErr != null) session.setAttribute("mensajeErr", mensajeErr);
    if (mensajeConfirmacion != null) session.setAttribute("mensajeConfirmacion", mensajeConfirmacion);

    StringBuilder url = new StringBuilder("vistaSesion.jsp");
    if (!estado.isEmpty() || (id != null && !id.isEmpty())) {
        url.append("?");
        boolean first = true;
        if (!estado.isEmpty()) {
            url.append("estado=").append(estado);
            first = false;
        }
        if (id != null && !id.isEmpty()) {
            if (!first) url.append("&");
            url.append("id=").append(id);
        }
    }

    response.sendRedirect(url.toString());
%>
