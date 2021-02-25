package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.CostCalculatorValidator;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertThrows;

public class CostCalculatorValidatorImplTest {

    private LocaleApplication localeApplication = new LocaleApplication();
    private ResourceBundle messages;
    private CostCalculatorValidator sut;

    @Before
    public void init() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
        messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        sut = new CostCalculatorValidatorImpl();
    }

    @Test
    public void testValidateCitiesParametersEmptyCityFromShouldThrowException() {
        //Given
        String cityFromId = "";
        String cityToId = "1";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCitiesParameters(localeApplication, cityFromId, cityToId));
        Assertions.assertEquals(messages.getString("exceptions.calculating.city_from"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCitiesParametersEmptyCityToShouldThrowException() {
        //Given
        String cityFromId = "1";
        String cityToId = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCitiesParameters(localeApplication, cityFromId, cityToId));
        Assertions.assertEquals(messages.getString("exceptions.calculating.city_to"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCitiesParametersNotNumericCityFromShouldThrowException() {
        //Given
        String cityFromId = "1";
        String cityToId = "string";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCitiesParameters(localeApplication, cityFromId, cityToId));
        Assertions.assertEquals(messages.getString("exceptions.calculating.city_from_numeric"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCitiesParametersNotNumericCityToShouldThrowException() {
        //Given
        String cityFromId = "string";
        String cityToId = "1";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCitiesParameters(localeApplication, cityFromId, cityToId));
        Assertions.assertEquals(messages.getString("exceptions.calculating.city_to_numeric"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCostParametersEmptyWeightShouldThrowException() {
        //Given
        String weight = "";
        String volume = "1";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCostParameters(localeApplication, weight, volume));
        Assertions.assertEquals(messages.getString("exceptions.calculating.weight"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCostParametersEmptyVolumeShouldThrowException() {
        //Given
        String weight = "1";
        String volume = "";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCostParameters(localeApplication, weight, volume));
        Assertions.assertEquals(messages.getString("exceptions.calculating.volume"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCostParametersNotNumericWeightShouldThrowException() {
        //Given
        String weight = "string";
        String volume = "1";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCostParameters(localeApplication, weight, volume));
        Assertions.assertEquals(messages.getString("exceptions.calculating.weight_numeric"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCostParametersNotNumericVolumeShouldThrowException() {
        //Given
        String weight = "1";
        String volume = "string";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCostParameters(localeApplication, weight, volume));
        Assertions.assertEquals(messages.getString("exceptions.calculating.volume_numeric"),
                validationException.getMessage());
    }

    @Test
    public void testValidateCostParametersZeroWeightShouldThrowException() {
        //Given
        String weight = "0";
        String volume = "1";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCostParameters(localeApplication, weight, volume));
        Assertions.assertEquals(messages.getString("exceptions.calculating.weight_zero"),
                validationException.getMessage());
    }
    @Test
    public void testValidateCostParametersZeroVolumeShouldThrowException() {
        //Given
        String weight = "1";
        String volume = "0";

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateCostParameters(localeApplication, weight, volume));
        Assertions.assertEquals(messages.getString("exceptions.calculating.volume_zero"),
                validationException.getMessage());
    }

    @Test
    public void testValidateSameCitiesSameShouldThrowException(){
        //Given
        CityViewDto cityFrom = new CityViewDto();
        cityFrom.setId(1);
        cityFrom.setName("someName");
        CityViewDto cityTo = new CityViewDto();
        cityTo.setId(1);
        cityTo.setName("someName");

        //When

        //Then
        ValidationException validationException = assertThrows(
                ValidationException.class, () -> sut.validateSameCities(localeApplication, cityFrom, cityTo));
        Assertions.assertEquals(messages.getString("exceptions.calculating.same_city"),
                validationException.getMessage());
    }
}