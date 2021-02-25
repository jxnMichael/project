package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.CreateReceiptPaymentValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertThrows;

public class CreateReceiptPaymentValidatorImplTest {

    private LocaleApplication localeApplication = new LocaleApplication();
    private ResourceBundle messages;
    private CreateReceiptPaymentValidator sut;

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
        messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        sut = new CreateReceiptPaymentValidatorImpl();
    }

    @Test
    public void testValidateOrderStatusBeforeCreatingReceiptCanceledShouldThrowException() {
        //Given
        int currentOrderStatusId = OrderStatusEnum.CANCELED.getID();
        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> sut.validateOrderStatusBeforeCreatingReceipt(localeApplication, currentOrderStatusId));
        Assertions.assertEquals(messages.getString("exception.error.creating_receipt_status"),
                validationException.getMessage());
    }

}