package com.tw.apistackbase.controller;

import com.google.common.collect.ImmutableList;
import com.tw.apistackbase.dto.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

import static com.google.common.collect.ImmutableList.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class CompanyControllerTest {
    private CompanyController companyController = new CompanyController();
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
    }

    @Test
    void should_return_company_when_create_new_company_succeed() throws URISyntaxException {
        ResponseEntity<Company> result = companyController.addCompany(company);

        assertEquals(CREATED, result.getStatusCode());
        assertEquals(company, result.getBody());
    }

    @Test
    void should_return_empty_companies_info_when_get_all_companies_succeed() {
        ImmutableList<Company> expectedCompanies = of();

        ResponseEntity<List<Company>>result = companyController.getAll();

        assertEquals(OK, result.getStatusCode());
        assertEquals(expectedCompanies, result.getBody());
    }
}
