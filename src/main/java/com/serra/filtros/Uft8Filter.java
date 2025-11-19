package com.serra.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/ejerciciosmvc/mastermind/*"})
public class Uft8Filter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
    throws IOException, ServletException {
        if (request.getCharacterEncoding()==null) {
            request.setCharacterEncoding("UTF-8");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Aplicando filtro UTF-8");

        filter.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
    
}
