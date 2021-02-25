package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.CityServiceImpl;
import com.final_project.cargo_delivery.service.implementation.DeliveryCalculatorServiceImpl;
import com.final_project.cargo_delivery.service.implementation.TypeCargosServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.service.interfaces.DeliveryCalculatorService;
import com.final_project.cargo_delivery.service.interfaces.TypeCargoService;
import com.final_project.cargo_delivery.validator.implementation.CostCalculatorValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.CostCalculatorValidator;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import com.final_project.cargo_delivery.web.dto.TypeCargoViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CalculateCommand calculates price for delivery and calculates time for delivery
 *
 * @author Mykhailo Hryb
 */
public class CalculateCommand extends Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorServiceImpl();
        CityService cityService = new CityServiceImpl();
        CostCalculatorValidator costCalculatorValidator = new CostCalculatorValidatorImpl();
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        TypeCargoService typeCargoService = new TypeCargosServiceImpl();

        String cityFromIdStr = request.getParameter("cityFromId");
        String cityToIdStr = request.getParameter("cityToId");
        String weightStr = request.getParameter("weight");
        String volumeStr = request.getParameter("volume");

        costCalculatorValidator.validateCitiesParameters(localeApplication, cityFromIdStr, cityToIdStr);
        costCalculatorValidator.validateCostParameters(localeApplication, weightStr, volumeStr);

        int cityFromId = Integer.parseInt(cityFromIdStr);
        int cityToId = Integer.parseInt(cityToIdStr);
        int weight = Integer.parseInt(weightStr);
        int volume = Integer.parseInt(volumeStr);

        LOGGER.info("cityFromId = {}", cityFromIdStr);
        LOGGER.info("cityToId = {}", cityToIdStr);
        LOGGER.info("weight = {}", weight);
        LOGGER.info("volume = {}", volume);

        CityViewDto cityFrom = cityService.getCityById(localeApplication, cityFromId);
        CityViewDto cityTo = cityService.getCityById(localeApplication, cityToId);

        costCalculatorValidator.validateSameCities(localeApplication, cityFrom, cityTo);

        int price = deliveryCalculatorService.calculatePriceDelivery(cityFrom, cityTo, weight, volume, localeApplication);
        request.setAttribute("calculated_price", price);

        LocalDate dateToDeliver = deliveryCalculatorService.getTimeToDelivery(cityFrom, cityTo, localeApplication);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToShowDeliver = dateFormatter.format(dateToDeliver);
        request.setAttribute("date_deliver", dateToShowDeliver);

        List<TypeCargoViewDto> typeCargoViewDtoList = typeCargoService.getAllTypesCargos(localeApplication);
        request.setAttribute("typeCargos", typeCargoViewDtoList);

        return Path.CALCULATED_RESULT;
    }

}
