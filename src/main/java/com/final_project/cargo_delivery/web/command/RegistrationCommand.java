package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Role;
import com.final_project.cargo_delivery.service.implementation.UserServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.validator.implementation.UserValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.UserValidator;
import com.final_project.cargo_delivery.web.dto.UserCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * RegistrationCommand.
 *
 * @author Mykhailo Hryb
 */
public class RegistrationCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserValidator userValidator = new UserValidatorImpl();
        UserService userService = new UserServiceImpl();
        UserCreateDto userCreateDto = extractUserFromRequest(request);
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");

        System.out.println("userCreateDto = " + userCreateDto);
        userValidator.validateUserCredentials(localeApplication,
                request.getParameter("email"), request.getParameter("password"));
        userValidator.validateNewUser(localeApplication, userCreateDto);
        UserViewDto userViewDto = userService.register(localeApplication, userCreateDto);
        HttpSession session = request.getSession();
        session.setAttribute("user", userViewDto);
        return Path.MAIN_PAGE;
    }

    /**
     * Sets UserCreateDto from user
     *
     * @param request
     * @return UserCreateDto
     */
    private UserCreateDto extractUserFromRequest(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        //Hashing password
        password = DigestUtils.md5Hex(password);
        int role = Role.AUTHORIZED.getID();
        return new UserCreateDto(firstName, lastName, email, role, password);
    }
}

