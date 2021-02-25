package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.OrderStatusConverter;
import com.final_project.cargo_delivery.dao.implementations.OrderStatusDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.OrderStatusDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatus;
import com.final_project.cargo_delivery.service.interfaces.OrderStatusService;
import com.final_project.cargo_delivery.web.dto.OrderStatusViewDto;

/**
 * OrderStatusService implementation
 *
 * @author Mykhailo Hryb
 */
public class OrderStatusServiceImpl implements OrderStatusService {

    private OrderStatusDao orderStatusDao = new OrderStatusDaoImpl();
    private OrderStatusConverter orderStatusConverter = new OrderStatusConverter();

    @Override
    public OrderStatusViewDto getOrderStatusById(LocaleApplication localeApplication, int id) {
        OrderStatus orderStatus = orderStatusDao.getOrderStatusById(localeApplication, id);
        return orderStatusConverter.convertOrderStatusToOrderStatusViewDto(orderStatus);
    }
}
