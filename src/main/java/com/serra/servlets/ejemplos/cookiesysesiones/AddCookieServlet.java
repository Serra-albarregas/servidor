package com.serra.servlets.ejemplos.cookiesysesiones;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/addcookieservlet"})
public class AddCookieServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("Clave", "Valor");
        cookie.setMaxAge(60*60);                            //Expiración de la cookie: 1 hora
        resp.setContentType("text/html;charset=UTF-8");
        resp.addCookie(cookie);
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang=\"es\">");
            out.println("  <head><title>Crear cookie</title></head>");
            out.println("  <body>");
            out.println("    <p>Cookie creada con exito</p>");
            out.println("  </body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
