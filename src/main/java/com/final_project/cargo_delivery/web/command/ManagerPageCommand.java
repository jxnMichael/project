package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.OrderServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.final_project.cargo_delivery.web.util.Pagination;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * ManagerPageCommand returns page with all orders for manager.
 *
 * @author Mykhailo Hryb
 */
public class ManagerPageCommand extends Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        OrderService orderService = new OrderServiceImpl();

        int countElements = orderService.getCountElements(localeApplication);
        Pagination pagination = new Pagination(request, countElements);
        Sorting sorting = new Sorting(request, "order_status_id");

        LocalDate currentDate = LocalDate.now();
        orderService.changeStatusByDate(localeApplication, currentDate);

        List<OrderViewDto> orderViewDtoList = orderService.getAllOrdersWithPagination(localeApplication,
                sorting.getSorting(), sorting.getTypeSorting(), pagination.getStep(), pagination.getPaginationItem());
//        LOGGER.info("orderViewDtoList = {}", orderViewDtoList);
        request.setAttribute("orderList", orderViewDtoList);
        return Path.MANAGER_PAGE;
    }
}
