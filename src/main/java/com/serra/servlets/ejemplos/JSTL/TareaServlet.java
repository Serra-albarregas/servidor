package com.serra.servlets.ejemplos.JSTL;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.serra.beans.ejemplos.Tarea;

@WebServlet("/tareasServlet")
public class TareaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el nombre de usuario
        String usuario = request.getParameter("usuario");
        usuario = usuario != null && !usuario.isEmpty() ? usuario : "Invitado";

        // Obtener la sesión
        HttpSession session = request.getSession();

        // Recuperar o inicializar lista de tareas
        List<Tarea> tareas = (List<Tarea>) session.getAttribute("tareas");
        if (tareas == null) {
            tareas = new ArrayList<>();
            session.setAttribute("tareas", tareas);
        }

        // Pasar el nombre del usuario como ejemplo de variable de sesión
        session.setAttribute("usuario", usuario);

        // Redirigir a la vista
        RequestDispatcher dispatcher = request.getRequestDispatcher("ejemplos/JSTL/tareasVista.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los parámetros del formulario
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        boolean completada = "on".equals(request.getParameter("completada"));

        // Validación de campos
        boolean hayError = false;
        StringBuilder mensajeError = new StringBuilder();

        if (titulo == null || titulo.trim().isEmpty()) {
            hayError = true;
            mensajeError.append("El título es obligatorio.<br>");
        }

        if (descripcion != null && descripcion.length() > 200) {
            hayError = true;
            mensajeError.append("La descripción no puede superar 200 caracteres.<br>");
        }

        // Obtener la sesión y la lista de tareas
        HttpSession session = request.getSession();
        List<Tarea> tareas = (List<Tarea>) session.getAttribute("tareas");
        if (tareas == null) {
            tareas = new ArrayList<>();
        }

        if (!hayError) {
            // Crear nueva tarea y añadir a la lista
            Tarea nueva = new Tarea(titulo, descripcion, completada);
            tareas.add(nueva);
            session.setAttribute("tareas", tareas);

            // Mensaje de éxito
            request.setAttribute("mensaje", "Tarea añadida correctamente");
        } else {
            // Mantener datos ingresados en caso de error
            request.setAttribute("titulo", titulo);
            request.setAttribute("descripcion", descripcion);
            request.setAttribute("completada", completada);
            request.setAttribute("mensaje", mensajeError.toString());
        }

        // Redirigir a la vista
        RequestDispatcher dispatcher = request.getRequestDispatcher("ejemplos/JSTL/tareasVista.jsp");
        dispatcher.forward(request, response);
    }

}
