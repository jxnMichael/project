package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RegistrationPageCommand .
 *
 * @author Mykhailo Hryb
 */
public class RegistrationPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return Path.PAGE_REGISTRATION;
    }
}
