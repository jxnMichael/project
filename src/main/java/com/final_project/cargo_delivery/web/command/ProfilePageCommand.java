package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.OrderServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;
import com.final_project.cargo_delivery.web.util.Pagination;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * ProfilePageCommand returns page with orders for user.
 *
 * @author Mykhailo Hryb
 */
public class ProfilePageCommand extends Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilePageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderServiceImpl();
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        HttpSession session = request.getSession();

        UserViewDto userViewDto = (UserViewDto) session.getAttribute("user");

        int countElements = orderService.getCountElementsByUser(localeApplication, userViewDto.getId());
        Pagination pagination = new Pagination(request, countElements);
        Sorting sorting = new Sorting(request, "order_status_id");

        LocalDate currentDate = LocalDate.now();
        LOGGER.info("current date {}", currentDate);
        orderService.changeStatusByDate(localeApplication, currentDate);

        List<OrderViewDto> orderViewDtoList = orderService.getUserOrders(localeApplication, userViewDto.getId(),
                sorting.getSorting(), sorting.getTypeSorting(), pagination.getStep(), pagination.getPaginationItem());
        request.setAttribute("orderList", orderViewDtoList);

//        LOGGER.info("orderViewDtoList = {}", orderViewDtoList);

        return Path.PROFILE_PAGE;
    }
}
