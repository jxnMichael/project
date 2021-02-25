package com.final_project.cargo_delivery.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LocalizationCommand sets localization from user.
 *
 * @author Mykhailo Hryb
 */
public class LocalizationCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cookie langCookie = new Cookie("lang", request.getParameter("lang"));
        if (request.getParameter("lang").equals("en_EN")) {
            langCookie.setMaxAge(0);
        } else {
            langCookie.setMaxAge(60 * 60 * 24 * 7);
        }
        response.addCookie(langCookie);
        return "";
    }
}
