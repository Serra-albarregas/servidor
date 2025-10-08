<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Conversor de divisas</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&family=Quicksand:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="../../css/estilos.css">
</head>
<body>

<%! 
    HashMap<String, Double> tasas;

    public void jspInit() {
        tasas = new HashMap<>();
        tasas.put("USD", 1.0);
        tasas.put("EUR", 0.93);
        tasas.put("GBP", 0.81);
        tasas.put("JPY", 144.0);
        tasas.put("AUD", 1.51);
        tasas.put("CAD", 1.35);
        tasas.put("CHF", 0.91);
        tasas.put("CNY", 7.2);
        tasas.put("SEK", 10.3);
        tasas.put("NZD", 1.59);
    }
%>

<%!
  public double calcularDivisa(double desde, String divDesde, String divHasta) throws IllegalArgumentException {
      if (!tasas.containsKey(divDesde) || !tasas.containsKey(divHasta)) {
          throw new IllegalArgumentException("Divisa no válida");
      }
      return desde * tasas.get(divHasta) / tasas.get(divDesde);
  }
%>

  <div class="container-pastel d-flex flex-column justify-content-center align-items-center min-vh-100">
    <div class="card pastel p-4 shadow-lg text-center" style="max-width: 460px; width: 100%;">
      <h1 class="display-pastel mb-4">Conversor de divisas</h1>

      <form class="text-start" method="get">
        <div class="mb-3">
          <label for="desde" class="form-label fw-semibold">Cantidad a convertir</label>
          <input type="number" step="0.01" id="desde" name="desde" class="form-control pastel" placeholder="Ej: 100.00" required>
        </div>

        <div class="row mb-4">
          <div class="col-6">
            <label for="divisaDesde" class="form-label fw-semibold">De</label>
            <select name="divisaDesde" id="divisaDesde" class="form-select pastel">
<%
            for (String divisa : tasas.keySet()) {
%>
                <option value="<%=divisa%>"><%=divisa%></option>
<%
            }
%>
            </select>
          </div>

          <div class="col-6">
            <label for="divisaHasta" class="form-label fw-semibold">A</label>
            <select name="divisaHasta" id="divisaHasta" class="form-select pastel">
<%
            for (String divisa : tasas.keySet()) {
%>
                <option value="<%=divisa%>"><%=divisa%></option>
<%
            }
%>
            </select>
          </div>
        </div>

        <div class="d-grid">
          <input type="submit" class="btn btn-lavender btn-lg" value="Convertir">
        </div>
      </form>
      <form>
        <div class="d-grid">
          <input type="submit" class="btn btn-lavender btn-lg" value="Borrar">
        </div>
      </form>

<%
    if (request.getParameter("desde") != null && 
        request.getParameter("divisaDesde") != null && 
        request.getParameter("divisaHasta") != null) {

        double desde = Double.parseDouble(request.getParameter("desde"));
        String divisaDesde = request.getParameter("divisaDesde");
        String divisaHasta = request.getParameter("divisaHasta");
        double resultado = calcularDivisa(desde, divisaDesde, divisaHasta);
%>

      <div class="mt-4 p-3 glass rounded">
        <h2 class="text-mint">Resultado</h2>
        <p class="fs-5 mb-0">
          <span class="badge pastel shadow-sm">
            <%= String.format("%.2f %s = %.2f %s", desde, divisaDesde, resultado, divisaHasta) %>
          </span>
        </p>
      </div>
<%
    }
%>
    </div>

    <footer class="footer-pastel mt-4">
      <small>Conversor de divisas</small>
      <br>
      <a href="/servidor-1.0">Volver</a>
    </footer>
  </div>

</body>
</html>
