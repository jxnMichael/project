package com.final_project.cargo_delivery.web.filters;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Role;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter. Sets command that available for different roles
 *
 * @author Mykhailo Hryb
 */
public class SecurityFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityFilter.class);
    private static final Map<Integer, List<String>> accessMap = new HashMap<>();
    private static final List<String> commons = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Setting pages for roles
        accessMap.put(Role.MANAGER.getID(), new ArrayList<>(
                Arrays.asList(
                        "manager-page",
                        "profile-page",
                        "make-order",
                        "create-receipt-payment",
                        "change-order-status",
                        "make-report"
                )
        ));
        accessMap.put(Role.AUTHORIZED.getID(), new ArrayList<>(
                Arrays.asList(
                        "profile-page",
                        "list-orders",
                        "make-order",
                        "change-order-status"
                )
        ));

        //Setting common pages
        commons.add("login");
        commons.add("login-page");
        commons.add("noCommand");
        commons.add("registration-page");
        commons.add("registration");
        commons.add("deliveries-page");
        commons.add("calculate-distance");
        commons.add("logout");
        commons.add("localization");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = httpServletRequest.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (accessAllowed(servletRequest)) {
            LOGGER.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessage = messages.getString("exception.error.dont_have_permission");

            servletRequest.setAttribute("errorMessage", errorMessage);
            LOGGER.trace("Set the request attribute: errorMessage --> " + errorMessage);

            servletRequest.getRequestDispatcher(Path.ERROR_PAGE)
                    .forward(servletRequest, servletResponse);
        }
    }

    /**
     * @param request
     * @return boolean if access for user is allowed
     */
    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty())
            return false;

        HttpSession session = httpRequest.getSession();
        UserViewDto user = (UserViewDto) session.getAttribute("user");
        if (user != null) {
            int userRoleId = user.getRoleId();
            if (userRoleId != 0) {
                return accessMap.get(userRoleId).contains(commandName) || commons.contains(commandName);
            }
        }
        return commons.contains(commandName);
    }

    @Override
    public void destroy() {
    }
}
