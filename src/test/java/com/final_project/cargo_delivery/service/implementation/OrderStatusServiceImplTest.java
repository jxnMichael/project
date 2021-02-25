package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.OrderStatusConverter;
import com.final_project.cargo_delivery.dao.interfaces.OrderStatusDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatus;
import com.final_project.cargo_delivery.service.interfaces.OrderStatusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderStatusServiceImplTest {

    @Mock
    OrderStatusDao orderStatusDao;

    @Mock
    OrderStatusConverter orderStatusConverter;

    @InjectMocks
    OrderStatusService sut = new OrderStatusServiceImpl();

    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testGetOrderStatusById() {
        //Given
        int orderStatusId = 1;
        OrderStatus orderStatus = new OrderStatus();
        Mockito.when(orderStatusDao.getOrderStatusById(localeApplication, orderStatusId)).thenReturn(orderStatus);

        //When
        sut.getOrderStatusById(localeApplication, orderStatusId);

        //Then
        Mockito.verify(orderStatusDao, Mockito.times(1))
                .getOrderStatusById(localeApplication, orderStatusId);
        Mockito.verifyNoMoreInteractions(orderStatusDao);
        Mockito.verify(orderStatusConverter, Mockito.times(1)).convertOrderStatusToOrderStatusViewDto(orderStatus);
    }

}