package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.service.implementation.OrderServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.validator.implementation.ChangeStatusValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.ChangeStatusValidator;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ChangeOrderStatusCommand changes status of order depended of form
 *
 * @author Mykhailo Hryb
 */
public class ChangeOrderStatusCommand extends Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOrderStatusCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String orderStatusRequest = request.getParameter("orderStatus");
        OrderService orderService = new OrderServiceImpl();
        ChangeStatusValidator changeStatusValidator = new ChangeStatusValidatorImpl();
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        String orderIdStr = request.getParameter("orderId");
        changeStatusValidator.validateOrderId(localeApplication, orderIdStr);

        long orderId = Long.parseLong(orderIdStr);
        int orderStatusId = -1;
        OrderViewDto orderViewDto = orderService.getOrderById(localeApplication, orderId);
        LOGGER.info("orderViewDto {}", orderViewDto);

        if (orderStatusRequest.equals("cancel")) {
            orderStatusId = OrderStatusEnum.CANCELED.getID();
            changeStatusValidator.validateStatusToCancel(localeApplication,
                    orderViewDto.getOrderStatusViewDto().getId());
        } else if (orderStatusRequest.equals("pay")) {
            orderStatusId = OrderStatusEnum.PAYED.getID();
            changeStatusValidator.validateStatusToPay(localeApplication,
                    orderViewDto.getOrderStatusViewDto().getId(), orderViewDto.getReceiptPath());
        } else {
            changeStatusValidator.validateTypeOfChangingStatus(localeApplication, orderStatusRequest);
        }

        orderService.changeOrderStatus(localeApplication, orderId, orderStatusId);
        return "";
    }
}
