package com.serra.servlets.ejemplos.cookiesysesiones;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logoutservlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang=\"es\">");
            out.println("  <head><title>Cerrar sesión</title></head>");
            out.println("  <body>");
            if (session != null) {
                session.invalidate();
                out.println("Sesión cerrada correctamente.");
            } else {
                out.println("No había sesión activa.");
            }
            out.println("  </body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
