package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.OrderStatus;
import com.final_project.cargo_delivery.web.dto.OrderStatusViewDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OrderStatusConverterTest {

    private OrderStatusConverter orderStatusConverter = new OrderStatusConverter();

    @Test
    public void testConvertOrderStatusToOrderStatusViewDto() {
        //Given
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatusId(1);
        orderStatus.setName("testName");
        orderStatus.setImagePath("testPath");

        //When
        OrderStatusViewDto orderStatusViewDto = orderStatusConverter.convertOrderStatusToOrderStatusViewDto(orderStatus);

        //Then
        Assertions.assertEquals(orderStatus.getOrderStatusId(), orderStatusViewDto.getId());
        Assertions.assertEquals(orderStatus.getName(), orderStatusViewDto.getName());
        Assertions.assertEquals(orderStatus.getImagePath(), orderStatusViewDto.getImagePath());

    }
}