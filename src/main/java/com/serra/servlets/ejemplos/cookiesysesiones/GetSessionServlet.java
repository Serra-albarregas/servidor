package com.serra.servlets.ejemplos.cookiesysesiones;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/getsessionservlet"})
public class GetSessionServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang=\"es\">");
            out.println("  <head><title>Obtener sesión</title></head>");
            out.println("  <body>");
            if (session != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date creationDate = new Date(session.getCreationTime());
                String formattedCreationTime = formatter.format(creationDate);
                
                out.println("<p>Id " + session.getId() + " Usuario " + session.getAttribute("usuario") + " Fecha de Creación " + formattedCreationTime);
                
            } else {
                out.println("<p>No se ha creado una sesión todavía</p>");
            }
            out.println("  </body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
