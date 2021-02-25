package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.City;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CityConverterTest {

    private CityConverter cityConverter = new CityConverter();

    @Test
    public void testConvertCityToCityViewDto() {
        //Given
        City city = new City();
        city.setId(1);
        city.setLongitude(11.1);
        city.setLatitude(22.2);
        city.setName("TestName");
        city.setImgPath("testPath");
        city.setIsForeign(false);

        //When
        CityViewDto cityViewDto = cityConverter.convertCityToCityViewDto(city);

        //Then
        Assertions.assertEquals(city.getId(), cityViewDto.getId());
        Assertions.assertEquals(city.getLongitude(), cityViewDto.getLongitude(), 4);
        Assertions.assertEquals(city.getLatitude(), cityViewDto.getLatitude(), 4);
        Assertions.assertEquals(city.getName(), cityViewDto.getName());
        Assertions.assertEquals(city.getIsForeign(), cityViewDto.getIsForeign());
        Assertions.assertEquals(city.getImgPath(), cityViewDto.getImgPath());
    }
}