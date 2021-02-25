package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.dao.interfaces.LocaleDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.Cookie;

@RunWith(MockitoJUnitRunner.class)
public class LocaleServiceImplTest {

    @Mock
    LocaleDao localeDao;

    @InjectMocks
    LocaleService sut = new LocaleServiceImpl();

    @Test
    public void testGetLocaleByCookieOrDefaultGetDefaultIfCookieEmpty() {
        //Given
        Cookie[] arrCookies = null;
        String defaultShortName = "en_EN";
        LocaleApplication predefinedLocale = new LocaleApplication();
        predefinedLocale.setShortName(defaultShortName);
        Mockito.when(localeDao.getLocaleByShortName(defaultShortName)).thenReturn(predefinedLocale);

        //When
        sut.getLocaleByCookieOrDefault(arrCookies);

        //Then
        Assertions.assertEquals(predefinedLocale.getShortName(), defaultShortName);
    }

    @Test
    public void testGetLocaleByCookieOrDefaultGetDefaultIfCookieNotEmpty() {
        //Given
        String shortName = "en_EN";
        Cookie[] arrCookies = {new Cookie("lang", shortName)};
        LocaleApplication predefinedLocale = new LocaleApplication();
        predefinedLocale.setShortName(shortName);
        Mockito.when(localeDao.getLocaleByShortName(shortName)).thenReturn(predefinedLocale);

        //When
        sut.getLocaleByCookieOrDefault(arrCookies);

        //Then
        Assertions.assertEquals(predefinedLocale.getShortName(), shortName);
        Mockito.verify(localeDao, Mockito.times(1)).getLocaleByShortName(shortName);
        Mockito.verifyNoMoreInteractions(localeDao);
    }

}