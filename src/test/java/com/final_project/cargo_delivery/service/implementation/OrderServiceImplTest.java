package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.OrderConverter;
import com.final_project.cargo_delivery.dao.interfaces.OrderDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.Order;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.web.dto.OrderCreateDto;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    OrderDao orderDao;

    @Mock
    OrderConverter orderConverter;

    @InjectMocks
    OrderService sut = new OrderServiceImpl();

    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testSaveOrder() {
        //Given
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        Order predefinedOrder = new Order();
        Mockito.when(orderConverter.convertOrderCreateDtoToOrder(orderCreateDto)).thenReturn(predefinedOrder);

        //When
        sut.saveOrder(orderCreateDto, localeApplication);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).saveOrder(predefinedOrder, localeApplication);
        Mockito.verifyNoMoreInteractions(orderDao);
        Mockito.verify(orderConverter, Mockito.times(1)).convertOrderCreateDtoToOrder(orderCreateDto);
        Mockito.verifyNoMoreInteractions(orderConverter);
    }

    @Test
    public void testGetFirstElementInPaginationShouldGetMaxPaginationStepIfOverflows() {
        //Given
        int paginationStep = 10;
        int paginationItem = 5;
        int predefinedCountElementsInDataBase = 35;
        int expectedFirstElements = 30;
        Mockito.when(orderDao.getCountElements(localeApplication)).thenReturn(predefinedCountElementsInDataBase);

        //When
        int resultFirstElem = sut.getFirstElementInPagination(localeApplication, paginationStep, paginationItem);

        //Then
        Assertions.assertEquals(expectedFirstElements, resultFirstElem);
        Mockito.verify(orderDao, Mockito.times(1)).getCountElements(localeApplication);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testGetFirstElementInPaginationShouldGetFirstElementOfNextStep() {
        //Given
        int paginationStep = 10;
        int paginationItem = 3;
        int predefinedCountElementsInDataBase = 35;
        int expectedFirstElements = 20;
        Mockito.when(orderDao.getCountElements(localeApplication)).thenReturn(predefinedCountElementsInDataBase);

        //When
        int resultFirstElem = sut.getFirstElementInPagination(localeApplication, paginationStep, paginationItem);

        //Then
        Assertions.assertEquals(expectedFirstElements, resultFirstElem);
        Mockito.verify(orderDao, Mockito.times(1)).getCountElements(localeApplication);
        Mockito.verifyNoMoreInteractions(orderDao);
    }


    @Test
    public void testGetUserOrders() {
        //Given
        long userId = 1;
        String orderBy = "id";
        TypeSorting typeSorting = TypeSorting.ASC;
        int paginationStep = 10;
        int paginationItem = 3;
        int firstElem = 20;
        List<Order> orderList = new ArrayList<>();
        Mockito.when(
                orderDao.getOrdersByUser(localeApplication, userId, orderBy, typeSorting, firstElem, paginationStep)
        ).thenReturn(orderList);
        Mockito.when(orderDao.getCountElements(localeApplication)).thenReturn(50);

        //When
        sut.getUserOrders(localeApplication, userId, orderBy, typeSorting, paginationStep, paginationItem);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByUser(localeApplication, userId, orderBy, typeSorting, firstElem, paginationStep);
        Mockito.verify(orderDao, Mockito.times(1)).getCountElements(localeApplication);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testGetAllOrders() {
        //Given
        String orderBy = "id";
        TypeSorting typeSorting = TypeSorting.ASC;
        List<Order> orderList = new ArrayList<>();
        Mockito.when(orderDao.getAllOrders(localeApplication, orderBy, typeSorting)).thenReturn(orderList);

        //When
        sut.getAllOrders(localeApplication, orderBy, typeSorting);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .getAllOrders(localeApplication, orderBy, typeSorting);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testGetAllOrdersWithPagination() {
        //Given
        String orderBy = "id";
        TypeSorting typeSorting = TypeSorting.ASC;
        int paginationStep = 10;
        int paginationItem = 3;
        int firstElem = 20;
        List<Order> orderList = new ArrayList<>();
        Mockito.when(
                orderDao.getAllOrdersWithPagination(localeApplication, orderBy, typeSorting, firstElem, paginationStep)
        ).thenReturn(orderList);
        Mockito.when(orderDao.getCountElements(localeApplication)).thenReturn(50);

        //When
        sut.getAllOrdersWithPagination(localeApplication, orderBy, typeSorting, paginationStep, paginationItem);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .getAllOrdersWithPagination(localeApplication, orderBy, typeSorting, firstElem, paginationStep);
        Mockito.verify(orderDao, Mockito.times(1)).getCountElements(localeApplication);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testGetOrderById() {
        //Given
        long id = 1;
        Order predefinedOrder = new Order();
        OrderViewDto predefinedOrderViewDto = new OrderViewDto();
        Mockito.when(orderDao.gerOrderById(localeApplication, id)).thenReturn(predefinedOrder);
        Mockito.when(orderConverter.convertOrderToOrderViewDto(localeApplication, predefinedOrder))
                .thenReturn(predefinedOrderViewDto);

        //When
        sut.getOrderById(localeApplication, id);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).gerOrderById(localeApplication, id);
        Mockito.verifyNoMoreInteractions(orderDao);
        Mockito.verify(orderConverter, Mockito.times(1))
                .convertOrderToOrderViewDto(localeApplication, predefinedOrder);
        Mockito.verifyNoMoreInteractions(orderConverter);
    }

    @Test
    public void testUpdateOrderReceiptPath() {
        //Given
        long id = 1;
        String receiptPath = "somePath";

        //When
        sut.updateOrderReceiptPath(localeApplication, id, receiptPath);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .updateOrderReceiptPath(localeApplication, id, receiptPath);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testChangeOrderStatus() {
        //Given
        long id = 1;
        int orderStatus = 1;

        //When
        sut.changeOrderStatus(localeApplication, id, orderStatus);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .changeOrderStatus(localeApplication, id, orderStatus);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testGetCountElements() {
        //Given

        //When
        sut.getCountElements(localeApplication);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).getCountElements(localeApplication);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testGetCountElementsByUser() {
        //Given
        long userId = 1;

        //When
        sut.getCountElementsByUser(localeApplication, userId);

        //Then
        Mockito.verify(orderDao, Mockito.times(1)).getCountElementsByUser(localeApplication, userId);
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testChangeStatusByDateShouldSetCanceledWhenOrderDateCreatedBeforeNow() {
        //Given
        List<Order> ordersNotRegistered = new ArrayList<>();
        long orderNotRegisteredId = 1;

        Order notRegisteredOrder = new Order();
        LocalDate dateCreated = LocalDate.now().minusDays(1);
        notRegisteredOrder.setId(orderNotRegisteredId);
        notRegisteredOrder.setDateCreated(dateCreated);

        ordersNotRegistered.add(notRegisteredOrder);
        List<Order> ordersNotPayed = new ArrayList<>();
        List<Order> ordersPayed = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        Mockito.when(orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_REGISTERED.getID()))
                .thenReturn(ordersNotRegistered);
        Mockito.when(orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_PAYED.getID()))
                .thenReturn(ordersNotPayed);
        Mockito.when(orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.PAYED.getID()))
                .thenReturn(ordersPayed);

        //When
        sut.changeStatusByDate(localeApplication, currentDate);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_REGISTERED.getID());
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_PAYED.getID());
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByStatus(localeApplication, OrderStatusEnum.PAYED.getID());
        Mockito.verify(orderDao, Mockito.times(1))
                .changeOrderStatus(localeApplication, orderNotRegisteredId, OrderStatusEnum.CANCELED.getID());
        Mockito.verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testChangeStatusByDateShouldSetDeliveredWhenOrderDateDeliverAfterNow() {
        //Given
        List<Order> ordersPayed = new ArrayList<>();
        long orderPayedId = 1;

        Order orderPayed = new Order();
        LocalDate dateDeliver = LocalDate.now().minusDays(1);
        orderPayed.setId(orderPayedId);
        orderPayed.setDateDelivery(dateDeliver);

        ordersPayed.add(orderPayed);
        List<Order> ordersNotRegistered = new ArrayList<>();
        List<Order> ordersNotPayed = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        Mockito.when(orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_REGISTERED.getID()))
                .thenReturn(ordersNotRegistered);
        Mockito.when(orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_PAYED.getID()))
                .thenReturn(ordersNotPayed);
        Mockito.when(orderDao.getOrdersByStatus(localeApplication, OrderStatusEnum.PAYED.getID()))
                .thenReturn(ordersPayed);

        //When
        sut.changeStatusByDate(localeApplication, currentDate);

        //Then
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_REGISTERED.getID());
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByStatus(localeApplication, OrderStatusEnum.NOT_PAYED.getID());
        Mockito.verify(orderDao, Mockito.times(1))
                .getOrdersByStatus(localeApplication, OrderStatusEnum.PAYED.getID());
        Mockito.verify(orderDao, Mockito.times(1))
                .changeOrderStatus(localeApplication, orderPayedId, OrderStatusEnum.DELIVERED.getID());
        Mockito.verifyNoMoreInteractions(orderDao);
    }
}