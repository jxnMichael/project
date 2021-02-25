package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.validator.interfaces.PaginationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PaginationValidatorImplTest {

    private PaginationValidator sut;
    private int defaultValue;
    private int count;

    @Before
    public void init() {
        sut = new PaginationValidatorImpl();
        this.defaultValue = 5;
        this.count = 10;
    }

    @Test
    public void testValidatePaginationStepShouldGetDefaultValueIfEmpty() {
        //Given
        String paginationStep = "";

        //When
        int resultValidationStep = sut.validatePagination(defaultValue, paginationStep, count);

        //Then
        Assertions.assertEquals(defaultValue, resultValidationStep);
    }

    @Test
    public void testValidatePaginationStepShouldGetDefaultValueIfPaginationIsNotNumeric() {
        //Given
        String paginationStep = "string";

        //When
        int resultValidationStep = sut.validatePagination(defaultValue, paginationStep, count);

        //Then
        Assertions.assertEquals(defaultValue, resultValidationStep);
    }

    @Test
    public void testValidatePaginationStepShouldGet1IfPaginationIsLess() {
        //Given
        String paginationStep = "0";

        //When
        int resultValidationStep = sut.validatePagination(defaultValue, paginationStep, count);

        //Then
        Assertions.assertEquals(1, resultValidationStep);
    }

    @Test
    public void testValidatePaginationStepShouldGetCountIfPaginationIsBigger() {
        //Given
        String paginationStep = "11";

        //When
        int resultValidationStep = sut.validatePagination(defaultValue, paginationStep, count);

        //Then
        Assertions.assertEquals(count, resultValidationStep);
    }
}