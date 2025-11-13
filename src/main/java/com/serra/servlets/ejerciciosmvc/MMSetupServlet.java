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

@WebServlet(name = "MMSetupServlet", urlPatterns = { "/mmSetupServlet" })
public class MMSetupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mensaje = "";
        boolean error = false;

        String nDigitosParam = req.getParameter("nDigitos");
        int nDigitos = 2;

        try {
            nDigitos = Validador.validarEntero(nDigitosParam, 2, 9);
            Mastermind mm = new Mastermind(nDigitos);
            HttpSession session = req.getSession(true);
            session.setAttribute("juego", mm);
        } catch (Exception e) {
            mensaje = e.getMessage();
            error=true;
        }

        String url = "";
        if (!error) {
            url = "ejerciciosmvc/mastermind/juego.jsp";
        } else {
            url = "ejerciciosmvc/mastermind/inicio.jsp";
            req.setAttribute("mensaje", mensaje);
        }
        req.getRequestDispatcher(url).forward(req,resp);
    }
}
