package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.CityConverter;
import com.final_project.cargo_delivery.dao.interfaces.CityDao;
import com.final_project.cargo_delivery.entity.City;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

    @Mock
    CityDao cityDao;

    @Mock
    CityConverter cityConverter;

    @InjectMocks
    CityService sut = new CityServiceImpl();

    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testGetAllCities() {
        //Given
        String orderBy = "id";
        List<City> predefinedListCities = new ArrayList<>();
        City cityToConvert = new City();
        predefinedListCities.add(cityToConvert);

        Mockito.when(cityDao.getAllCities(localeApplication, orderBy)).thenReturn(predefinedListCities);

        //When
        sut.getAllCities(localeApplication, orderBy);

        //Then
        Mockito.verify(cityDao, Mockito.times(1)).getAllCities(localeApplication, orderBy);
        Mockito.verifyNoMoreInteractions(cityDao);
        Mockito.verify(cityConverter).convertCityToCityViewDto(cityToConvert);
    }

    @Test
    public void testGetAllCitiesWithFilterIsForeign() {
        //Given
        String orderBy = "id";
        int isForeign = 1;
        List<City> predefinedListCities = new ArrayList<>();
        City cityToConvert = new City();
        predefinedListCities.add(cityToConvert);
        Mockito.when(cityDao.getAllCitiesWithFilterIsForeign(localeApplication, orderBy, isForeign))
                .thenReturn(predefinedListCities);

        //When
        sut.getAllCitiesWithFilterIsForeign(localeApplication, orderBy, isForeign);

        //Then
        Mockito.verify(cityDao, Mockito.times(1))
                .getAllCitiesWithFilterIsForeign(localeApplication, orderBy, isForeign);
        Mockito.verifyNoMoreInteractions(cityDao);
        Mockito.verify(cityConverter).convertCityToCityViewDto(cityToConvert);
    }

    @Test
    public void testGetCityById() {
        //Given
        int cityId = 1;
        City predefinedCity = new City();
        Mockito.when(cityDao.getCityById(cityId, localeApplication))
                .thenReturn(predefinedCity);

        //When
        sut.getCityById(localeApplication, cityId);

        //Then
        Mockito.verify(cityDao, Mockito.times(1))
                .getCityById(cityId, localeApplication);
        Mockito.verifyNoMoreInteractions(cityDao);
        Mockito.verify(cityConverter, Mockito.times(1))
                .convertCityToCityViewDto(predefinedCity);
    }
}