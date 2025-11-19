package com.serra.filtros;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class SanitizingWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> sanitizedParams;

    public SanitizingWrapper(HttpServletRequest request) {
        super(request);
        sanitizedParams = new HashMap<>();

        request.getParameterMap().forEach((key, values) -> {
            String[] sanitizedValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                sanitizedValues[i] = escapeHtml(values[i]);
            }
            sanitizedParams.put(key, sanitizedValues);
        });
    }

    String escapeHtml(String s) {
    if (s == null) return "";
    return s.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;");
    }

    @Override
    public String getParameter(String name) {
        String[] values = sanitizedParams.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return sanitizedParams.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return sanitizedParams;
    }
}
