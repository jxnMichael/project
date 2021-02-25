package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Order;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.service.interfaces.OrderStatusService;
import com.final_project.cargo_delivery.service.interfaces.TypeCargoService;
import com.final_project.cargo_delivery.service.interfaces.UserService;
import com.final_project.cargo_delivery.web.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class OrderConverterTest {

    @Mock
    CityService cityService;

    @Mock
    TypeCargoService typeCargoService;

    @Mock
    UserService userService;

    @Mock
    OrderStatusService orderStatusService;
    @InjectMocks
    private OrderConverter orderConverter = new OrderConverter();

    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testConvertOrderCreateDtoToOrder() {
        //Given
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setCityFromId(1);
        orderCreateDto.setCityToId(1);
        orderCreateDto.setDateCreated(LocalDate.now());
        orderCreateDto.setOrderStatusId(1);
        orderCreateDto.setPrice(100);
        orderCreateDto.setVolume(1);
        orderCreateDto.setWeight(1);
        orderCreateDto.setUserId(1);
        orderCreateDto.setTypeCargoId(1);
        orderCreateDto.setDateDelivery(LocalDate.now().plusDays(1));

        //When
        Order order = orderConverter.convertOrderCreateDtoToOrder(orderCreateDto);

        //Then
        Assertions.assertEquals(orderCreateDto.getAddress(), order.getAddress());
        Assertions.assertEquals(orderCreateDto.getDateCreated(), order.getDateCreated());
        Assertions.assertEquals(orderCreateDto.getOrderStatusId(), order.getOrderStatusId());
        Assertions.assertEquals(orderCreateDto.getCityFromId(), order.getCityFromId());
        Assertions.assertEquals(orderCreateDto.getCityToId(), order.getCityToId());
        Assertions.assertEquals(orderCreateDto.getDateDelivery(), order.getDateDelivery());
        Assertions.assertEquals(orderCreateDto.getPrice(), order.getPrice());
        Assertions.assertEquals(orderCreateDto.getUserId(), order.getUserId());
        Assertions.assertEquals(orderCreateDto.getVolume(), order.getVolume());
        Assertions.assertEquals(orderCreateDto.getWeight(), order.getWeight());
        Assertions.assertEquals(orderCreateDto.getTypeCargoId(), order.getTypeCargoId());
    }

    @Test
    public void testConvertOrderToOrderViewDto() {
        //Given
        Order order = new Order();
        order.setId(1);
        order.setCityFromId(1);
        order.setCityToId(2);
        order.setDateCreated(LocalDate.now());
        order.setOrderStatusId(1);
        order.setPrice(100);
        order.setVolume(1);
        order.setWeight(1);
        order.setUserId(1);
        order.setTypeCargoId(1);
        order.setDateDelivery(LocalDate.now().plusDays(1));

        CityViewDto predefinedCityFromViewDto = new CityViewDto();
        predefinedCityFromViewDto.setId(order.getCityFromId());

        CityViewDto predefinedCityToViewDto = new CityViewDto();
        predefinedCityToViewDto.setId(order.getCityToId());

        TypeCargoViewDto predefinedTypeCargoViewDto = new TypeCargoViewDto();
        predefinedTypeCargoViewDto.setId(order.getTypeCargoId());

        UserViewDto predefinedUserViewDto = new UserViewDto();
        predefinedUserViewDto.setId(order.getUserId());

        OrderStatusViewDto predefinedOrderStatusViewDto = new OrderStatusViewDto();
        predefinedOrderStatusViewDto.setId(order.getOrderStatusId());

        Mockito.when(cityService.getCityById(localeApplication, order.getCityFromId()))
                .thenReturn(predefinedCityFromViewDto);
        Mockito.when(cityService.getCityById(localeApplication, order.getCityToId()))
                .thenReturn(predefinedCityToViewDto);
        Mockito.when(typeCargoService.getTypeCargoById(localeApplication, order.getTypeCargoId()))
                .thenReturn(predefinedTypeCargoViewDto);
        Mockito.when(userService.getUserById(localeApplication, order.getUserId()))
                .thenReturn(predefinedUserViewDto);
        Mockito.when(orderStatusService.getOrderStatusById(localeApplication, order.getOrderStatusId()))
                .thenReturn(predefinedOrderStatusViewDto);

        //When
        OrderViewDto orderViewDto = orderConverter.convertOrderToOrderViewDto(localeApplication, order);

        //Then
        Assertions.assertEquals(order.getAddress(), orderViewDto.getAddress());
        Assertions.assertEquals(order.getDateCreated(), orderViewDto.getDateCreated());
        Assertions.assertEquals(order.getOrderStatusId(), orderViewDto.getOrderStatusViewDto().getId());
        Assertions.assertEquals(order.getCityFromId(), orderViewDto.getCityFromViewDto().getId());
        Assertions.assertEquals(order.getCityToId(), orderViewDto.getCityToViewDto().getId());
        Assertions.assertEquals(order.getDateDelivery(), orderViewDto.getDateDelivery());
        Assertions.assertEquals(order.getPrice(), orderViewDto.getPrice());
        Assertions.assertEquals(order.getUserId(), orderViewDto.getUser().getId());
        Assertions.assertEquals(order.getVolume(), orderViewDto.getVolume());
        Assertions.assertEquals(order.getWeight(), orderViewDto.getWeight());
        Assertions.assertEquals(order.getTypeCargoId(), orderViewDto.getTypeCargoViewDto().getId());

        Mockito.verify(cityService, Mockito.times(1)).getCityById(localeApplication, order.getCityFromId());
        Mockito.verify(cityService, Mockito.times(1)).getCityById(localeApplication, order.getCityToId());
        Mockito.verifyNoMoreInteractions(cityService);
        Mockito.verify(typeCargoService, Mockito.times(1)).getTypeCargoById(localeApplication, order.getTypeCargoId());
        Mockito.verifyNoMoreInteractions(typeCargoService);
        Mockito.verify(userService, Mockito.times(1)).getUserById(localeApplication, order.getUserId());
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verify(orderStatusService, Mockito.times(1)).getOrderStatusById(localeApplication, order.getOrderStatusId());
        Mockito.verifyNoMoreInteractions(orderStatusService);
    }

}