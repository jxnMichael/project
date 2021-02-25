package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.TypeCargo;
import com.final_project.cargo_delivery.web.dto.TypeCargoViewDto;

/**
 * TypeCargoConverter
 *
 * @author Mykhailo Hryb
 */
public class TypeCargoConverter {

    /**
     * Converts TypeCargo to TypeCargoViewDto
     *
     * @param typeCargo
     * @return
     */
    public TypeCargoViewDto convertTypeCargoToTypeCargoViewDto(TypeCargo typeCargo) {
        TypeCargoViewDto typeCargoViewDto = new TypeCargoViewDto();
        typeCargoViewDto.setId(typeCargo.getId());
        typeCargoViewDto.setName(typeCargo.getName());
        typeCargoViewDto.setImagePath(typeCargo.getImagePath());
        return typeCargoViewDto;
    }
}
