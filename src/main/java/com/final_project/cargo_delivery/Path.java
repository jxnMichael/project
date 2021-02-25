package com.final_project.cargo_delivery;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author Mykhailo Hryb
 */
public class Path {

    // pages
    public static final String PAGE_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String MAIN_PAGE = "/index.jsp";
    public static final String ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String DELIVERY_PAGE = "/WEB-INF/jsp/deliveries_page.jsp";
    public static final String CALCULATED_RESULT = "/WEB-INF/jsp/calculated_result.jsp";
    public static final String PROFILE_PAGE = "/WEB-INF/jsp/profile.jsp";
    public static final String MANAGER_PAGE = "/WEB-INF/jsp/manager/admin_page.jsp";

    // PDF report files
    public static final String REPORT_BY_CITY = "/WEB-INF/jsp/manager/pdfs/reportByCities.pdf";
    public static final String REPORT_BY_DATE = "/WEB-INF/jsp/manager/pdfs/reportByDate.pdf";

}
