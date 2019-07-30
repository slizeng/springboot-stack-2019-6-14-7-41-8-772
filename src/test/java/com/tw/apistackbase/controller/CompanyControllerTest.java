package com.tw.apistackbase.controller;

import com.google.common.collect.ImmutableList;
import com.tw.apistackbase.dto.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyControllerTest {
    private CompanyController companyController = new CompanyController();
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
    }

    @Test
    void should_return_company_when_create_new_company_succeed() {
        Company result = companyController.addCompany(company);

        assertEquals(company, result);
    }

    @Test
    void should_return_empty_companies_info_when_get_all_companies_succeed() {
        ImmutableList<Company> expectedCompanies = of();

        List<Company> result = companyController.getAll();

        assertEquals(expectedCompanies, result);
    }
}
