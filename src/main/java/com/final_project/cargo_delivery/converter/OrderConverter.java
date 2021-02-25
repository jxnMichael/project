package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Order;
import com.final_project.cargo_delivery.service.implementation.CityServiceImpl;
import com.final_project.cargo_delivery.service.implementation.OrderStatusServiceImpl;
import com.final_project.cargo_delivery.service.implementation.TypeCargosServiceImpl;
import com.final_project.cargo_delivery.service.implementation.UserServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.service.interfaces.OrderStatusService;
import com.final_project.cargo_delivery.service.interfaces.TypeCargoService;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.web.dto.*;

/**
 * OrderConverter
 *
 * @author Mykhailo Hryb
 */
public class OrderConverter {

    private CityService cityService = new CityServiceImpl();
    private TypeCargoService typeCargoService = new TypeCargosServiceImpl();
    private UserService userService = new UserServiceImpl();
    private OrderStatusService orderStatusService = new OrderStatusServiceImpl();
    private OrderViewDto orderViewDto = new OrderViewDto();

    /**
     * Converts orderCreateDto to Order
     *
     * @param orderCreateDto
     * @return
     */
    public Order convertOrderCreateDtoToOrder(OrderCreateDto orderCreateDto) {
        Order order = new Order();
        order.setDateDelivery(orderCreateDto.getDateDelivery());
        order.setAddress(orderCreateDto.getAddress());
        order.setOrderStatusId(orderCreateDto.getOrderStatusId());
        order.setTypeCargoId(orderCreateDto.getTypeCargoId());
        order.setWeight(orderCreateDto.getWeight());
        order.setVolume(orderCreateDto.getVolume());
        order.setPrice(orderCreateDto.getPrice());
        order.setUserId(orderCreateDto.getUserId());
        order.setCityFromId(orderCreateDto.getCityFromId());
        order.setCityToId(orderCreateDto.getCityToId());
        order.setDateCreated(orderCreateDto.getDateCreated());
        return order;
    }

    /**
     * Converts Order to OrderViewDto
     *
     * @param localeApplication
     * @param order
     * @return
     */
    public OrderViewDto convertOrderToOrderViewDto(LocaleApplication localeApplication, Order order) {
        CityViewDto cityFromViewDto = cityService.getCityById(localeApplication, order.getCityFromId());
        CityViewDto cityToViewDto = cityService.getCityById(localeApplication, order.getCityToId());
        TypeCargoViewDto typeCargoViewDto = typeCargoService.getTypeCargoById(localeApplication, order.getTypeCargoId());
        UserViewDto userViewDto = userService.getUserById(localeApplication, order.getUserId());
        OrderStatusViewDto orderStatusViewDto =
                orderStatusService.getOrderStatusById(localeApplication, order.getOrderStatusId());

        orderViewDto.setId(order.getId());
        orderViewDto.setAddress(order.getAddress());
        orderViewDto.setCityFromViewDto(cityFromViewDto);
        orderViewDto.setCityToViewDto(cityToViewDto);
        orderViewDto.setOrderStatusViewDto(orderStatusViewDto);
        orderViewDto.setTypeCargoViewDto(typeCargoViewDto);
        orderViewDto.setUser(userViewDto);
        orderViewDto.setPrice(order.getPrice());
        orderViewDto.setWeight(order.getWeight());
        orderViewDto.setVolume(order.getVolume());
        orderViewDto.setDateDelivery(order.getDateDelivery());
        orderViewDto.setDateCreated(order.getDateCreated());
        orderViewDto.setReceiptPath(order.getReceiptPath());

        return orderViewDto;
    }
}
