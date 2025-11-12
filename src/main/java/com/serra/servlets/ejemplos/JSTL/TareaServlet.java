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

        //Obtener el nombre de usuario
        String usuario = request.getParameter("usuario") != null ? request.getParameter("usuario"):"Invitado";

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

        // Obtener la sesión y la lista de tareas
        HttpSession session = request.getSession();
        List<Tarea> tareas = (List<Tarea>) session.getAttribute("tareas");

        if (tareas == null) {
            tareas = new ArrayList<>();
        }

        // Crear nueva tarea
        Tarea nueva = new Tarea(titulo, descripcion, completada);
        tareas.add(nueva);

        // Guardar lista actualizada
        session.setAttribute("tareas", tareas);

        // Mensaje temporal en request (solo dura una petición)
        request.setAttribute("mensaje", "Tarea añadida correctamente");

        // Redirigir a la vista
        RequestDispatcher dispatcher = request.getRequestDispatcher("ejemplos/JSTL/tareasVista.jsp");
        dispatcher.forward(request, response);
    }
}
