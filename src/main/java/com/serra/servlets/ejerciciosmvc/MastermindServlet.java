package com.serra.servlets.ejerciciosmvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.serra.beans.Mastermind;
import com.serra.utils.Validador;

@WebServlet(name = "MastermindServlet", urlPatterns = { "/ejerciciosmvc/mastermind/mastermindServlet" })
public class MastermindServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String intento = req.getParameter("intento");
        HttpSession session = req.getSession(false);
        String mensaje = "";
        boolean error = false;

        /*if (session == null) {
            resp.sendRedirect("inicio.jsp");
            return;
        }*/

        Mastermind mm = (Mastermind) session.getAttribute("juego");
        try {
            intento = Validador.validarTamanioStr(intento, mm.getnDigitos());
        } catch (Exception e) {
            mensaje = e.getMessage();
            error = true;
        }

        if (!error) {
            boolean ganar = false;
            try {
                ganar = mm.nuevoTurno(intento);
            } catch (Exception e) {
                mensaje = e.getMessage();
            }

            if (ganar) {
                req.setAttribute("ganar", true);
            }
        }

        req.setAttribute("mensaje", mensaje);
        req.getRequestDispatcher("juego.jsp").forward(req, resp);
    }
}
