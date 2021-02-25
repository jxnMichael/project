package com.final_project.cargo_delivery.web.command;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.service.implementation.OrderServiceImpl;
import com.final_project.cargo_delivery.service.implementation.ReportServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.OrderService;
import com.final_project.cargo_delivery.service.interfaces.ReportService;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * MakeReportCommand.
 *
 * @author Mykhailo Hryb
 */
public class MakeReportCommand extends Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(MakeReportCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LocaleApplication localeApplication = (LocaleApplication) request.getAttribute("locale");
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        ReportService reportService = new ReportServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        Sorting sorting = new Sorting(request, "order_status_id");
        List<OrderViewDto> orderViewDtoList =
                orderService.getAllOrders(localeApplication, sorting.getSorting(), sorting.getTypeSorting());

        String typeReport = request.getParameter("typeReport");
        String pathToReport = "";
        if (typeReport.equals("report_by_city")) {
            pathToReport = reportService.makeReportByCity(localeApplication, orderViewDtoList);
        } else if (typeReport.equals("report_by_dates")) {
            pathToReport = reportService.makeReportByDate(localeApplication, orderViewDtoList);
        } else {
            throw new ValidationException(messages.getString("exception.error.type_report"));
        }

        request.setAttribute("pathToReport", pathToReport);

        return pathToReport;
    }
}
