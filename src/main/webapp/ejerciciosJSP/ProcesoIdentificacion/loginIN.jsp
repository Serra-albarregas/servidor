<%@page session="false"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.serra.utils.CookieUtils"%>
<%!
    private static final String USUARIO = "admin";
    private static final String PASSWORD = "1234";
%>
<%
    String nombre = request.getParameter("nombre");
    String pass = request.getParameter("pass");
    String remember = request.getParameter("remember");
    String mensaje = "";

    // Comprobación de estado bloqueado
    if (CookieUtils.getCookie(request, "bloqueo") != null) {
        mensaje = "Estas bloqueado, espera un rato";
        response.sendRedirect("login.jsp?mensaje=" + mensaje);
        return;
    }

    // Comprobación de campos vacíos
    if (nombre==null || nombre.trim().isEmpty() || pass==null || pass.trim().isEmpty()) {
        mensaje = "Error, los campos no pueden estar vacíos";
        response.sendRedirect("login.jsp?mensaje=" + mensaje);
        return;
    }

    // Comprobación de usuario y contraseña correctos
    if (!USUARIO.equals(nombre) || !PASSWORD.equals(pass)) {
        
        Cookie estadoBloqueo = CookieUtils.getCookie(request, "estadoBloqueo");
        int estado = 1;
        if (estadoBloqueo==null) {
            estadoBloqueo = new Cookie("estadoBloqueo", String.valueOf(estado));
        } else {
            estado = Integer.parseInt(estadoBloqueo.getValue());
            if (estado<4) {
                estado++;
            }
            estadoBloqueo = new Cookie("estadoBloqueo", String.valueOf(estado));
        }
        estadoBloqueo.setMaxAge(60*60*24);
        response.addCookie(estadoBloqueo);
        Cookie cookieBloqueo=null;
        switch (estado){
            case 1:
            case 2:
                mensaje = "Error, el usuario o la contraseña no coincide. Te quedan " + (3-estado) + " intentos";
            break;
            case 3:
                mensaje = "Se ha bloqueado el acceso durante 30 segundos";
                cookieBloqueo = new Cookie("bloqueo", "true");
                cookieBloqueo.setMaxAge(30);
                response.addCookie(cookieBloqueo);
            break;
            case 4:
                mensaje = "Se ha bloqueado el acceso durante 3 minutos";
                cookieBloqueo = new Cookie("bloqueo", "true");
                cookieBloqueo.setMaxAge(60*3);
                response.addCookie(cookieBloqueo);
            break;
        }
        
        response.sendRedirect("login.jsp?mensaje=" + mensaje);
        return;
    }

    CookieUtils.deleteCookie(request, response, "estadoBloqueo");

    // Comprobación de recordar usuario
    if ("on".equals(remember)) {
        Cookie rememberCookie = new Cookie("remember", nombre);
        rememberCookie.setMaxAge(60*60*24*7);
        response.addCookie(rememberCookie);
    } else {
        CookieUtils.deleteCookie(request, response, "remember");
    }

    //Creación de la sesión y almacenamiento de los atributos de sesión
    HttpSession session = request.getSession(true);
    session.setAttribute("usuario", nombre);
    
    Cookie nuevoAcceso = CookieUtils.getCookie(request, "nuevoAcceso");
    Cookie ultimoAcceso = null;
    LocalDateTime currentDateTime = LocalDateTime.now();
    
    if (nuevoAcceso!=null) {
        ultimoAcceso= new Cookie("ultimoAcceso", nuevoAcceso.getValue());
        ultimoAcceso.setMaxAge(60*60*24*7);
        response.addCookie(ultimoAcceso);
    }
    nuevoAcceso = new Cookie("nuevoAcceso", currentDateTime.toString());
    nuevoAcceso.setMaxAge(60*60*24*7);
    response.addCookie(nuevoAcceso);

    response.sendRedirect("menu.jsp");
%>