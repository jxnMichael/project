package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Page to login.
 *
 * @author Mykhailo Hryb
 */
public class LoginPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return Path.PAGE_LOGIN;
    }
}
