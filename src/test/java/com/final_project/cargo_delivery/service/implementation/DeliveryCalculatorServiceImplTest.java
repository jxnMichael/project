package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.DeliveryCalculatorService;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class DeliveryCalculatorServiceImplTest {

    private CityViewDto cityFrom = new CityViewDto();
    private CityViewDto cityTo = new CityViewDto();
    private DeliveryCalculatorService deliveryCalculatorService;
    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void init() {
        cityFrom.setId(1);
        cityFrom.setLatitude(48.4647);
        cityFrom.setLongitude(35.0462);

        cityTo.setId(2);
        cityTo.setLatitude(49.8397);
        cityTo.setLongitude(24.0297);

        deliveryCalculatorService = new DeliveryCalculatorServiceImpl();
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testCalculatePriceDeliveryFromDniproToLvivShouldReturn81() {
        //Given
        int expectedPrice = 81;
        int weight = 1;
        int volume = 1;

        //When
        int resultPrice =
                deliveryCalculatorService.calculatePriceDelivery(this.cityFrom, this.cityTo, weight, volume, localeApplication);

        //Then
        Assert.assertEquals(expectedPrice, resultPrice);
    }

    @Test
    public void tesGetTimeToDeliveryFromDniproToLvivShouldReturn2() {
        //Given
        LocalDate expectedDate = LocalDate.now().plusDays(2);

        //When
        LocalDate resultDate =
                deliveryCalculatorService.getTimeToDelivery(this.cityFrom, this.cityTo, localeApplication);

        //Then
        Assertions.assertEquals(expectedDate, resultDate);
    }

}