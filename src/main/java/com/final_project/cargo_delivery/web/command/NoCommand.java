package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * No command.
 *
 * @author Mykhailo Hryb
 */
public class NoCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger LOGGER = LoggerFactory.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        LOGGER.error("Set the request attribute: errorMessage --> " + errorMessage);

        LOGGER.debug("Command finished");
        return Path.ERROR_PAGE;
    }

}