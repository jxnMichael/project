package com.final_project.cargo_delivery.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LogOutCommand.
 *
 * @author Mykhailo Hryb
 */
public class LogOutCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return "";
    }
}
