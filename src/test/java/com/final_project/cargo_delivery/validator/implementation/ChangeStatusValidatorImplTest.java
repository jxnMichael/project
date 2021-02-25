package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.ChangeStatusValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertThrows;

public class ChangeStatusValidatorImplTest {

    private LocaleApplication localeApplication = new LocaleApplication();
    private ResourceBundle messages;
    private ChangeStatusValidator sut;

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
        messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        sut = new ChangeStatusValidatorImpl();
    }

    @Test
    public void testValidateOrderIdEmptyShouldThrowException() {
        //Given
        String orderId = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateOrderId(localeApplication, orderId));
        Assert.assertEquals(messages.getString("exception.error.cannot_find_order"),
                validationException.getMessage());
    }

    @Test
    public void testValidateOrderIdNotNumericShouldThrowException() {
        //Given
        String orderId = "string";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateOrderId(localeApplication, orderId));
        Assert.assertEquals(messages.getString("exception.error.cannot_find_order"),
                validationException.getMessage());
    }

    @Test
    public void testValidateStatusToPayShouldThrowExceptionIfStatusIncorrect() {
        //Given
        int currentStatus = OrderStatusEnum.PAYED.getID();
        String pdfFile = "path";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateStatusToPay(localeApplication, currentStatus, pdfFile));
        Assert.assertEquals(messages.getString("exception.error.status_cannot_pay"),
                validationException.getMessage());
    }

    @Test
    public void testValidateStatusToPayShouldThrowExceptionIfReceiptPdfPathEmpty() {
        //Given
        int currentStatus = OrderStatusEnum.NOT_PAYED.getID();
        String pdfFile = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateStatusToPay(localeApplication, currentStatus, pdfFile));
        Assert.assertEquals(messages.getString("exception.error.status_pay_without_receipt"),
                validationException.getMessage());
    }

    @Test
    public void testValidateStatusToCancelShouldThrowExceptionIfStatusCanceled() {
        //Given
        int currentStatus = OrderStatusEnum.CANCELED.getID();

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateStatusToCancel(localeApplication, currentStatus));
        Assertions.assertEquals(messages.getString("exception.error.status_cancel_canceled"),
                validationException.getMessage());
    }

    @Test
    public void testValidateStatusToCancelShouldThrowExceptionIfStatusPayed() {
        //Given
        int currentStatus = OrderStatusEnum.PAYED.getID();

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateStatusToCancel(localeApplication, currentStatus));
        Assertions.assertEquals(messages.getString("exception.error.status_cancel_payed"),
                validationException.getMessage());
    }

    @Test
    public void testValidateStatusToCancelShouldThrowExceptionIfStatusDelivered() {
        //Given
        int currentStatus = OrderStatusEnum.DELIVERED.getID();

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateStatusToCancel(localeApplication, currentStatus));
        Assertions.assertEquals(messages.getString("exception.error.status_cancel_delivered"),
                validationException.getMessage());
    }

    @Test
    public void testValidateTypeOfChangingStatusThrowExceptionIfNotCancelOrPay() {
        //Given
        String type = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateTypeOfChangingStatus(localeApplication, type));
        Assertions.assertEquals(messages.getString("exception.error.status_type"),
                validationException.getMessage());
    }

}