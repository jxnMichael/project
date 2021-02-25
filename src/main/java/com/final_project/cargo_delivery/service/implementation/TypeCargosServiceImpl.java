package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.TypeCargoConverter;
import com.final_project.cargo_delivery.dao.implementations.TypeCargoDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.TypeCargoDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.TypeCargo;
import com.final_project.cargo_delivery.service.interfaces.TypeCargoService;
import com.final_project.cargo_delivery.web.dto.TypeCargoViewDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TypeCargosService implementation
 *
 * @author Mykhailo Hryb
 */
public class TypeCargosServiceImpl implements TypeCargoService {

    private TypeCargoDao typeCargoDao = new TypeCargoDaoImpl();

    private TypeCargoConverter typeCargoConverter = new TypeCargoConverter();

    @Override
    public List<TypeCargoViewDto> getAllTypesCargos(LocaleApplication localeApplication) {
        List<TypeCargo> typeCargoList = typeCargoDao.getAllTypesCargos(localeApplication);
        return typeCargoList.stream().map(
                typeCargoItem -> typeCargoConverter.convertTypeCargoToTypeCargoViewDto(typeCargoItem)
        ).collect(Collectors.toList());
    }

    @Override
    public TypeCargoViewDto getTypeCargoById(LocaleApplication localeApplication, int typeCargoId) {
        TypeCargo typeCargo = typeCargoDao.getCargoById(localeApplication, typeCargoId);
        return typeCargoConverter.convertTypeCargoToTypeCargoViewDto(typeCargo);
    }
}
