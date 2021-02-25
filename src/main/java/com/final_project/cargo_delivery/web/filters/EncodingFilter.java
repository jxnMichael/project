package com.final_project.cargo_delivery.web.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Security filter. Sets command that available for different roles
 *
 * @author Mykhailo Hryb
 */
public class EncodingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingFilter.class);

    private String encoding;

    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        // do nothing
        LOGGER.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        LOGGER.debug("Filter starts");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        LOGGER.trace("Request uri --> " + httpRequest.getRequestURI());

        String requestEncoding = request.getCharacterEncoding();
        if (requestEncoding == null) {
            LOGGER.trace("Request encoding = null, set encoding --> " + encoding);
            request.setCharacterEncoding(encoding);
        }

        LOGGER.debug("Filter finished");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        LOGGER.debug("Filter initialization starts");
        encoding = fConfig.getInitParameter("encoding");
        LOGGER.trace("Encoding from web.xml --> " + encoding);
        LOGGER.debug("Filter initialization finished");
    }

}
