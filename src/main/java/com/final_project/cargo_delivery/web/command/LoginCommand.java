package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ApplicationException;
import com.final_project.cargo_delivery.service.implementation.UserServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.validator.implementation.UserValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.UserValidator;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LoginCommand.
 *
 * @author Mykhailo Hryb
 */
public class LoginCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            throw new ApplicationException("You have already logged in");
        }
        UserValidator userValidator = new UserValidatorImpl();
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        UserService userService = new UserServiceImpl();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //Hashing password
        password = DigestUtils.md5Hex(password);

        UserViewDto userViewDto = userService.login(localeApplication, email, password);
        userValidator.validateUserCredentials(localeApplication, email, password);
        userValidator.isCorrectPasswordAndEmail(localeApplication, userViewDto, email, password);
        session.setAttribute("user", userViewDto);
        return Path.MAIN_PAGE;
    }
}
