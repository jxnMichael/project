package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.validator.interfaces.SortingValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SortingValidatorImplTest {

    private SortingValidator sut;

    @Before
    public void initLocalization() {
        sut = new SortingValidatorImpl();
    }

    @Test
    public void testValidateSortBySetDefaultIfSortByEmpty() {
        //Given
        String sortBy = "";
        String defaultValue = "id";

        //When
        String resultSort = sut.validateSortBy(sortBy, defaultValue);

        //Then
        Assert.assertEquals(defaultValue, resultSort);
    }

    @Test
    public void testValidateTypeSortingShouldGetASCIfTypeEmpty() {
        //Given
        String typeSort = "";
        TypeSorting predefinedTypeSorting = TypeSorting.ASC;

        //When
        TypeSorting typeSorting = sut.validateTypeSorting(typeSort);

        //Then
        Assertions.assertEquals(predefinedTypeSorting, typeSorting);
    }
    @Test
    public void testValidateTypeSortingShouldGetDESC() {
        //Given
        String typeSort = "DESC";
        TypeSorting predefinedTypeSorting = TypeSorting.DESC;

        //When
        TypeSorting typeSorting = sut.validateTypeSorting(typeSort);

        //Then
        Assertions.assertEquals(predefinedTypeSorting, typeSorting);
    }
}