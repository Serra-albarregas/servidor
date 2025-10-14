<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Resultado cambio</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="../../css/estilos.css">
</head>
<body>

<%!
    String[] imagenes = {"c1.jpg", "c2.jpg", "c5.jpg", "c10.jpg", "c20.jpg", "c50.jpg", "1.jpg", "2.jpg", "5.png", "10.png", "20.png", "50.png", "100.png", "200.png", "500.png"};
%>

<%!
public int[] calcularCambio(float input) {
    int[] valores = {1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};
    int[] cambio = new int[valores.length];
    int resto = (int) Math.floor(input * 100);
    int i = valores.length - 1;
    while (resto > 0) {
        if (resto >= valores[i]) {
            cambio[i]++;
            resto -= valores[i];
        } else {
            i--;
        }
    }
    return cambio;
}
%>

<%
    float total = Float.parseFloat(request.getParameter("inputTotal"));
    float pagado = Float.parseFloat(request.getParameter("inputPagado"));
    float cambio = pagado - total;
    int[] monedas = calcularCambio(cambio);
%>

<div class="container-pastel text-center py-5">
  <div class="card pastel p-4 shadow-lg">

<%
    if (total>pagado) {
%>
        <h1 class="display-pastel mb-3 text-danger">Error de validación</h1>
        <p class="fs-5 mb-4">El importe pagado debe ser mayor que el total.</p>
<%
    } else if (total<0 || pagado < 0) {
%>
        <h1 class="display-pastel mb-3 text-danger">Error de validación</h1>
        <p class="fs-5 mb-4">Los importes no pueden ser negativos.</p>
<%
    } else {
%>
    <h1 class="display-pastel mb-3">Resultado del cambio</h1>
    <p class="fs-5 mb-4">El cambio total es: 
      <span class="badge pastel"><%= String.format("%.2f €", cambio) %></span>
    </p>

    <div class="glass p-3 d-flex flex-wrap justify-content-center gap-3">
<% 
        for (int i = monedas.length - 1; i >= 0; i--) {
            for (int j = 0; j < monedas[i]; j++) { 
%>
                <img src="../../images/<%=imagenes[i]%>" alt="dinero" height="90">
<%          }
        }
    }
%>
    </div>

    <a href="formulario.html" class="btn btn-mint mt-4">⬅ Volver</a>
  </div>

  <footer class="footer-pastel mt-4">
    <small>Calculadora de cambio - Resultado</small>
    <br>
      <a href="/servidor-1.0">Volver</a>
  </footer>
</div>

</body>
</html>
