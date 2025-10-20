package com.serra.servlets.ejerciciosSoloServlet.contadorVisitasSesion;

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

@WebServlet(urlPatterns = { "/contadorVisitas" })
public class ContadorVisitas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession(true);
        String nombre = req.getParameter("nombre");

        if (nombre == null || nombre.isEmpty()) {
            nombre = "Visitante";
        }

        // Recuperar y actualizar contador
        Integer visitas = (Integer) session.getAttribute("visitas");
        boolean primera = false;
        if (visitas == null){
            visitas = 0;
            primera = true;
        }
        visitas++;
        session.setAttribute("visitas", visitas);

        // Formatear fechas
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationTime = formatter.format(new Date(session.getCreationTime()));
        String lastAccess = formatter.format(new Date(session.getLastAccessedTime()));

        // Construcción de respuesta HTML
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang='es'>");
            out.println("<head><meta charset='UTF-8'><title>Contador de Visitas</title></head>");
            out.println("<body>");
            if (primera) {
                out.println("<p>Hola, " + escapar(nombre) + "!</p>");
                out.println("<p>ID de sesión: " + session.getId() + "</p>");
                out.println("<p>Fecha de creación: " + creationTime + "</p>");
            } else {
                out.println("<p>Último acceso: " + lastAccess + "</p>");
            }
            out.println("<p>Visitas en esta sesión: <b>" + visitas + "</b></p>");
            out.println("<a href='contadorVisitas'>Recargar página</a><br><br>");
            
            // Botón POST para eliminar sesión
            out.println("<form method='post' action='contadorVisitas'>");
            out.println("  <input type='hidden' name='action' value='logout'/>");
            out.println("  <button type='submit'>Eliminar sesión</button>");
            out.println("</form>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            // Redirigir con GET (para evitar reenvío de formulario)
            resp.sendRedirect("ejerciciosServlets/contadorVisitasSesion/formulario.html");
        } else {
            // En caso de acción desconocida
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no reconocida");
        }
    }

    public String escapar(String s) {
      if (s == null) return "";
      return s.replace("&", "&amp;")
              .replace("<", "&lt;")
              .replace(">", "&gt;")
              .replace("\"", "&quot;")
              .replace("'", "&#x27;");
    }
}
