package com.tw.apistackbase.controller;

import com.google.common.collect.ImmutableList;
import com.tw.apistackbase.dto.Company;
import com.tw.apistackbase.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

class CompanyControllerTest {
    private CompanyController companyController = new CompanyController();
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company(1, "Ali");
    }

    @Test
    void should_return_company_when_create_new_company_succeed() throws URISyntaxException {
        ResponseEntity<Company> result = companyController.addCompany(company);

        assertEquals(CREATED, result.getStatusCode());
        assertEquals(company, result.getBody());
        assertEquals(1, companyController.getAll().getBody().size());
    }

    @Test
    void should_return_empty_companies_info_when_get_all_companies_succeed() {
        ImmutableList<Company> expectedCompanies = of();

        ResponseEntity<List<Company>> result = companyController.getAll();

        assertEquals(OK, result.getStatusCode());
        assertEquals(expectedCompanies, result.getBody());
    }

    @Test
    void should_return_specific_company_when_query_an_existing_company_by_id_succeed() throws URISyntaxException {
        companyController.addCompany(company);

        ResponseEntity<Company> result = companyController.getCompanyById(1);

        assertEquals(OK, result.getStatusCode());
        assertEquals(company, result.getBody());
    }

    @Test
    void should_return_not_found_when_query_an_company_with_non_existing_id() {
        ResponseEntity<Company> result = companyController.getCompanyById(2);

        assertEquals(NOT_FOUND, result.getStatusCode());
    }

    @Test
    void should_return_all_specific_employees_when_obtain_employees_under_a_specific_company() throws URISyntaxException {
        List<Employee> employees = company.getEmployees();
        companyController.addCompany(company);

        ResponseEntity<List<Employee>> result = companyController.getEmployees(1);

        assertEquals(OK, result.getStatusCode());
        assertEquals(employees, result.getBody());
    }

    @Test
    void should_return_not_found_when_obtain_employees_under_a_non_existing_company() {
        ResponseEntity<List<Employee>> result = companyController.getEmployees(2);

        assertEquals(NOT_FOUND, result.getStatusCode());
    }

    @Test
    void should_return_paged_companies_when_get_paged_companies_succeed() throws URISyntaxException {
        Company firstCompany = new Company();
        Company secondCompany = new Company();
        Company thirdCompany = new Company();
        companyController.addCompany(firstCompany);
        companyController.addCompany(secondCompany);
        companyController.addCompany(thirdCompany);

        ResponseEntity<List<Company>> firstResult = companyController.getAllWithPagination(1, 2);
        ResponseEntity<List<Company>> secondResult = companyController.getAllWithPagination(2, 2);

        assertEquals(OK, firstResult.getStatusCode());
        assertEquals(of(firstCompany, secondCompany), firstResult.getBody());

        assertEquals(OK, secondResult.getStatusCode());
        assertEquals(of(thirdCompany), secondResult.getBody());
    }

    @Test
    void should_return_bad_request_when_get_paged_companies_but_page_is_exceed() throws URISyntaxException {
        companyController.addCompany(company);

        ResponseEntity<List<Company>> result = companyController.getAllWithPagination(2, 1);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }
}
