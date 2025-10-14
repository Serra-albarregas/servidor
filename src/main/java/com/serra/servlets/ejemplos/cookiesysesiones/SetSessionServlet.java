package com.serra.servlets.ejemplos.cookiesysesiones;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/setsessionservlet"})
public class SetSessionServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);         // Obtención de la sesión, si no existe se crea
        session.setMaxInactiveInterval(600);                // Tiempo de expiración de sesión: 10 minutos (600 segundos)
        session.setAttribute("usuario", "Alfredo");

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang=\"es\">");
            out.println("  <head><title>Crear sesión</title></head>");
            out.println("  <body>");
            out.println("    <p>Sesión creada con exito: " + session.getId() + "</p>");
            out.println("  </body>");
            out.println("</html>");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
