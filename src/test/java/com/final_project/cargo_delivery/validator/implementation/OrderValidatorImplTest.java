package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.OrderValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertThrows;

public class OrderValidatorImplTest {

    private LocaleApplication localeApplication = new LocaleApplication();
    private ResourceBundle messages;
    private OrderValidator sut;

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
        messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        sut = new OrderValidatorImpl();
    }

    @Test
    public void testValidateOrderParametersEmptyTypeCargoShouldThrowException() {
        //Given
        String typeCargo = "";
        String address = "someAddress";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateOrderParameters(localeApplication, typeCargo, address));
        Assertions.assertEquals(messages.getString("exceptions.calculating.typeCargo"),
                validationException.getMessage());
    }

    @Test
    public void testValidateOrderParametersEmptyAddressShouldThrowException() {
        //Given
        String typeCargo = "1";
        String address = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateOrderParameters(localeApplication, typeCargo, address));
        Assertions.assertEquals(messages.getString("exceptions.calculating.address"),
                validationException.getMessage());
    }
}