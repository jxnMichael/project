package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.service.implementation.CityServiceImpl;
import com.final_project.cargo_delivery.service.implementation.DeliveryCalculatorServiceImpl;
import com.final_project.cargo_delivery.service.implementation.OrderServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.service.interfaces.DeliveryCalculatorService;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.validator.implementation.CostCalculatorValidatorImpl;
import com.final_project.cargo_delivery.validator.implementation.OrderValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.CostCalculatorValidator;
import com.final_project.cargo_delivery.validator.interfaces.OrderValidator;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import com.final_project.cargo_delivery.web.dto.OrderCreateDto;
import com.final_project.cargo_delivery.web.dto.UserViewDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

/**
 * MakeOrderCommand.
 *
 * @author Mykhailo Hryb
 */
public class MakeOrderCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        OrderService orderService = new OrderServiceImpl();
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        HttpSession session = request.getSession();
        UserViewDto userViewDto = (UserViewDto) session.getAttribute("user");
        OrderCreateDto orderCreateDto = extractOrderFromRequest(request, response, localeApplication, userViewDto);
        orderService.saveOrder(orderCreateDto, localeApplication);

        return "";
    }

    /**
     * Sets orderCreateDto from user
     *
     * @param request
     * @param response
     * @param localeApplication
     * @param userViewDto       user from session
     * @return OrderCreateDto
     */
    private OrderCreateDto extractOrderFromRequest(HttpServletRequest request, HttpServletResponse response,
                                                   LocaleApplication localeApplication, UserViewDto userViewDto) {
        CityService cityService = new CityServiceImpl();
        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorServiceImpl();
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        CostCalculatorValidator costCalculatorValidator = new CostCalculatorValidatorImpl();
        OrderValidator orderValidator = new OrderValidatorImpl();

        //getting parameters from request
        String typeCargoStr = request.getParameter("typeOfCargo");
        String cityFromIdStr = request.getParameter("cityFromId");
        String cityToIdStr = request.getParameter("cityToId");
        String weightStr = request.getParameter("weight");
        String address = request.getParameter("addressDelivery");
        String volumeStr = request.getParameter("volume");

        costCalculatorValidator.validateCitiesParameters(localeApplication, cityFromIdStr, cityToIdStr);
        costCalculatorValidator.validateCostParameters(localeApplication, weightStr, volumeStr);
        orderValidator.validateOrderParameters(localeApplication, typeCargoStr, address);

        //getting right dataTypes
        int cityFromId = Integer.parseInt(cityFromIdStr);
        int cityToId = Integer.parseInt(cityToIdStr);
        int weight = Integer.parseInt(weightStr);
        int volume = Integer.parseInt(volumeStr);
        int typeCargo = Integer.parseInt(typeCargoStr);

        //getting cities
        CityViewDto cityFrom = cityService.getCityById(localeApplication, cityFromId);
        CityViewDto cityTo = cityService.getCityById(localeApplication, cityToId);

        costCalculatorValidator.validateSameCities(localeApplication, cityFrom, cityTo);

        int price = deliveryCalculatorService.calculatePriceDelivery(cityFrom, cityTo, weight, volume, localeApplication);
        LocalDate dateCreated = LocalDate.now();
        LocalDate dateToDeliver = deliveryCalculatorService.getTimeToDelivery(cityFrom, cityTo, localeApplication);

        //setting orderViewDto
        orderCreateDto.setAddress(address);
        orderCreateDto.setCityFromId(cityFromId);
        orderCreateDto.setOrderStatusId(OrderStatusEnum.NOT_REGISTERED.getID());
        orderCreateDto.setCityToId(cityToId);
        orderCreateDto.setWeight(weight);
        orderCreateDto.setVolume(volume);
        orderCreateDto.setPrice(price);
        orderCreateDto.setDateCreated(dateCreated);
        orderCreateDto.setDateDelivery(dateToDeliver);
        orderCreateDto.setTypeCargoId(typeCargo);
        orderCreateDto.setUserId(userViewDto.getId());

        return orderCreateDto;
    }
}
