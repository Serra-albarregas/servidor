package com.serra.servlets.ejemplos.cookiesysesiones;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/getcookieservlet" })
public class GetCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang=\"es\">");
            out.println("  <head><title>Obtener cookies</title></head>");
            out.println("  <body>");
            out.println("<ul>");
            for (Cookie cookie : cookies) {
                out.println("  <li>Clave: " + cookie.getName() + " Valor: " + cookie.getValue() + "</li>");
            }
            out.println("</ul>");
            out.println("  </body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
