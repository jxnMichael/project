package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.TypeCargoConverter;
import com.final_project.cargo_delivery.dao.interfaces.TypeCargoDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.TypeCargo;
import com.final_project.cargo_delivery.service.interfaces.TypeCargoService;
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
public class TypeCargosServiceImplTest {

    @Mock
    TypeCargoDao typeCargoDao;

    @Mock
    TypeCargoConverter typeCargoConverter;

    @InjectMocks
    TypeCargoService sut = new TypeCargosServiceImpl();

    private LocaleApplication localeApplication = new LocaleApplication();

    @Before
    public void initLocalization() {
        localeApplication.setShortName("en_EN");
        localeApplication.setIdLocale(1);
    }

    @Test
    public void testGetAllCargos() {
        //Given
        List<TypeCargo> typeCargoList = new ArrayList<>();
        TypeCargo typeCargoToConvert = new TypeCargo();
        typeCargoList.add(typeCargoToConvert);
        Mockito.when(typeCargoDao.getAllTypesCargos(localeApplication)).thenReturn(typeCargoList);

        //When
        sut.getAllTypesCargos(localeApplication);

        //Then
        Mockito.verify(typeCargoDao, Mockito.times(1)).getAllTypesCargos(localeApplication);
        Mockito.verifyNoMoreInteractions(typeCargoDao);
        Mockito.verify(typeCargoConverter).convertTypeCargoToTypeCargoViewDto(typeCargoToConvert);
    }

    @Test
    public void testGetCargoById() {
        //Given
        int cargoId = 1;
        TypeCargo typeCargo = new TypeCargo();
        Mockito.when(typeCargoDao.getCargoById(localeApplication, cargoId)).thenReturn(typeCargo);

        //When
        sut.getTypeCargoById(localeApplication, cargoId);

        //Then
        Mockito.verify(typeCargoDao, Mockito.times(1)).getCargoById(localeApplication, cargoId);
        Mockito.verifyNoMoreInteractions(typeCargoDao);
        Mockito.verify(typeCargoConverter).convertTypeCargoToTypeCargoViewDto(typeCargo);
    }

}