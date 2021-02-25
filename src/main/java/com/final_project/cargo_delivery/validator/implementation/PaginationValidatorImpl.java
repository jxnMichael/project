package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.validator.interfaces.PaginationValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * PaginationValidator implementation
 *
 * @author Mykhailo Hryb
 */
public class PaginationValidatorImpl implements PaginationValidator {

    public final static int DEFAULT_PAGINATION_STEP = 5;

    public final static int DEFAULT_PAGINATION_STEP_ITEM = 1;

    @Override
    public int validatePagination(int defaultValue, String paginationStep, int countElements) {
        int validationStep = defaultValue;
        if (!StringUtils.isEmpty(paginationStep) && StringUtils.isNumeric(paginationStep)) {
            validationStep = Integer.parseInt(paginationStep);
        }
        if (validationStep < 1) {
            validationStep = 1;
        }
        if (validationStep > countElements) {
            validationStep = countElements;
        }
        return validationStep;
    }
}
