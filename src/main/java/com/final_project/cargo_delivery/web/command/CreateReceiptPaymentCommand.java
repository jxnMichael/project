package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.service.implementation.OrderServiceImpl;
import com.final_project.cargo_delivery.service.implementation.ReceiptPaymentServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.service.interfaces.ReceiptPaymentService;
import com.final_project.cargo_delivery.validator.implementation.ChangeStatusValidatorImpl;
import com.final_project.cargo_delivery.validator.implementation.CreateReceiptPaymentValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.ChangeStatusValidator;
import com.final_project.cargo_delivery.validator.interfaces.CreateReceiptPaymentValidator;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CreateReceiptPaymentCommand.
 *
 * @author Mykhailo Hryb
 */
public class CreateReceiptPaymentCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        ReceiptPaymentService receiptPaymentService = new ReceiptPaymentServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        ChangeStatusValidator changeStatusValidator = new ChangeStatusValidatorImpl();
        CreateReceiptPaymentValidator createReceiptPaymentValidator = new CreateReceiptPaymentValidatorImpl();
        String orderIdStr = request.getParameter("orderId");

        //validating orderId
        changeStatusValidator.validateOrderId(localeApplication, orderIdStr);

        long orderId = Long.parseLong(orderIdStr);
        OrderViewDto orderViewDto = orderService.getOrderById(localeApplication, orderId);

        //validating orderStatus
        createReceiptPaymentValidator.validateOrderStatusBeforeCreatingReceipt(localeApplication,
                orderViewDto.getOrderStatusViewDto().getId());

        String receiptPath = receiptPaymentService.createReceiptPaymentForUser(localeApplication, orderViewDto);
        orderService.updateOrderReceiptPath(localeApplication, orderId, receiptPath);

        if (orderViewDto.getOrderStatusViewDto().getId() == OrderStatusEnum.NOT_REGISTERED.getID()) {
            orderService.changeOrderStatus(localeApplication, orderId, OrderStatusEnum.NOT_PAYED.getID());
        }
        return "";
    }
}
