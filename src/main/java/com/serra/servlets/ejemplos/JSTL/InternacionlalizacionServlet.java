package com.serra.servlets.ejemplos.JSTL;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.serra.utils.Validador;

@WebServlet(urlPatterns = {"/ejemplos/JSTL/internacionalizacion"})
public class InternacionlalizacionServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("usuario");
        String dineroStr = req.getParameter("dinero");
        int dinero = 0;
        String idioma = req.getParameter("idioma");

        String mensaje = "";
        boolean error = false;
        System.out.println(TimeZone.getTimeZone("Europe/Paris"));
        try {
            nombre = Validador.validarString(nombre);
            dinero = Validador.validarEntero(dineroStr);
            idioma = Validador.validarString(idioma);
        } catch (IllegalArgumentException e){
            mensaje = e.getMessage();
            error = true;
            req.setAttribute("mensaje", mensaje);
        }

        if (!error) {
            HttpSession session = req.getSession(true);
            session.setAttribute("usuario", nombre);
            session.setAttribute("dinero", dinero);
            session.setAttribute("lang", idioma);
            Cookie cookie = new Cookie("lang", idioma);
            resp.addCookie(cookie);

            req.setAttribute("hora", new Date());
        }

        req.getRequestDispatcher("internacionalizacionVista.jsp").forward(req, resp);
    }
}
