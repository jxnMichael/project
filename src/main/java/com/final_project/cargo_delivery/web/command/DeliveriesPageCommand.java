package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.Path;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.CityServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * DeliveriesPageCommand sets cities to deliver from/to.
 *
 * @author Mykhailo Hryb
 */
public class DeliveriesPageCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CityService cityService = new CityServiceImpl();
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        List<CityViewDto> cityViewDtoList;
        Sorting sorting = new Sorting(request, "id");


        String isForeignStr = request.getParameter("is_foreign");
        if (!StringUtils.isEmpty(isForeignStr)) {
            int isForeign = Integer.parseInt(isForeignStr);
            cityViewDtoList = cityService.getAllCitiesWithFilterIsForeign(localeApplication, sorting.getSorting(), isForeign);
        } else {
            cityViewDtoList = cityService.getAllCities(localeApplication, sorting.getSorting());
        }
        request.setAttribute("cities", cityViewDtoList);

        return Path.DELIVERY_PAGE;
    }

}
