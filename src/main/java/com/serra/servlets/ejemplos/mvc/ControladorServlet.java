package com.serra.servlets.ejemplos.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.serra.beans.ejemplos.Gato;

@WebServlet(name = "ControladorServlet", urlPatterns = {"/ControladorServlet"})
public class ControladorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtención de parámetros
        String nombre = request.getParameter("nombre");
        String edadStr = request.getParameter("edad");

        String mensajeError = null;
        int edad = 0;

        // Filtro de validación
        if (nombre == null || nombre.trim().isEmpty()) {
            mensajeError = "El nombre no puede estar vacío.";
        } else if (edadStr == null || edadStr.trim().isEmpty()) {
            mensajeError = "La edad no puede estar vacía.";
        } else {
            try {
                edad = Integer.parseInt(edadStr);
                if (edad < 0) {
                    mensajeError = "La edad no puede ser negativa.";
                }
            } catch (NumberFormatException e) {
                mensajeError = "La edad debe ser un número entero.";
            }
        }

        if (mensajeError != null) {
            request.setAttribute("error", mensajeError);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ejemplos/mvc/Vista.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Crear el bean del modelo
        Gato gato = new Gato(nombre, edad);

        // Guardar en la sesión
        HttpSession session = request.getSession();
        if (session.getAttribute(nombre)!=null){
            request.setAttribute("enSesion", "El gato ya estaba guardado.");
        } else {
            request.setAttribute("enSesion", "El gato se ha guardado por primera vez.");
        }
        session.setAttribute(nombre, gato);

        // Calcular información derivada
        int edadHumana = gato.calcularEdadHumana();

        // Pasar datos a la vista
        request.setAttribute("gato", gato);
        request.setAttribute("edadHumana", edadHumana);

        // Redirigir de nuevo a la vista
        RequestDispatcher dispatcher = request.getRequestDispatcher("ejemplos/mvc/Vista.jsp");
        dispatcher.forward(request, response);
    }
}
