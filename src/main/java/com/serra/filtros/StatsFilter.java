package com.serra.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class StatsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        long inicio = System.currentTimeMillis();

        String ip   = request.getRemoteAddr();
        String uri  = req.getRequestURI();
        String meth = req.getMethod();

        System.out.println("[LOG] " + meth + " " + uri + " desde " + ip + " - INICIO");

        filter.doFilter(request, response);

        long fin = System.currentTimeMillis();
        long tiempo = fin - inicio;

        System.out.println("[LOG] " + meth + " " + uri + " - FIN en " + tiempo + " ms");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
