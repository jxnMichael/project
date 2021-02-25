package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.TypeCargo;
import com.final_project.cargo_delivery.web.dto.TypeCargoViewDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TypeCargoConverterTest {

    private TypeCargoConverter typeCargoConverter = new TypeCargoConverter();

    @Test
    public void convertTypeCargoToTypeCargoViewDto() {
        //Given
        TypeCargo typeCargo = new TypeCargo();
        typeCargo.setId(1);
        typeCargo.setName("testName");
        typeCargo.setImagePath("testPath");

        //When
        TypeCargoViewDto typeCargoViewDto = typeCargoConverter.convertTypeCargoToTypeCargoViewDto(typeCargo);

        //Then
        Assertions.assertEquals(typeCargo.getId(), typeCargoViewDto.getId());
        Assertions.assertEquals(typeCargo.getName(), typeCargoViewDto.getName());
        Assertions.assertEquals(typeCargo.getImagePath(), typeCargoViewDto.getImagePath());
    }
}