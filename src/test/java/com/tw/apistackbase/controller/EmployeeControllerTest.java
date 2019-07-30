package com.tw.apistackbase.controller;

import com.tw.apistackbase.dto.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static com.tw.apistackbase.dto.Gender.FEMALE;
import static com.tw.apistackbase.dto.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeControllerTest {
    private EmployeeController employeeController = new EmployeeController();
    private Employee firstEmploy;
    private Employee secondEmploy;
    private Employee thirdEmploy;

    @BeforeEach
    void setUp() {
        firstEmploy = new Employee(1, "first", 18, MALE, 1000);
        secondEmploy = new Employee(2, "second", 18, FEMALE, 1000);
        thirdEmploy = new Employee(3, "third", 18, MALE, 1000);
    }

    @Test
    void should_return_added_employee_when_add_an_employee_succeed() throws URISyntaxException {
        ResponseEntity<Employee> result = employeeController.addEmployee(firstEmploy);

        assertEquals(CREATED, result.getStatusCode());
        assertEquals(firstEmploy, result.getBody());
    }

    @Test
    void should_return_paged_employee_succeed() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);
        employeeController.addEmployee(secondEmploy);
        employeeController.addEmployee(thirdEmploy);
        List<Employee> firstPageEmployees = of(firstEmploy, secondEmploy);
        List<Employee> secondPageEmployees = of(thirdEmploy);

        ResponseEntity<List<Employee>> firstQueryResult = employeeController.getEmployees(null, 1, 2);
        ResponseEntity<List<Employee>> secondQueryResult = employeeController.getEmployees(null, 2, 2);

        assertEquals(OK, firstQueryResult.getStatusCode());
        assertEquals(firstPageEmployees, firstQueryResult.getBody());
        assertEquals(OK, secondQueryResult.getStatusCode());
        assertEquals(secondPageEmployees, secondQueryResult.getBody());
    }

    @Test
    void should_return_bad_request_when_page_exceed() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);

        ResponseEntity<List<Employee>> result = employeeController.getEmployees(null, 2, 2);

        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void should_return_filtered_employee_when_get_all_employees_by_gender() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);
        employeeController.addEmployee(secondEmploy);
        employeeController.addEmployee(thirdEmploy);
        List<Employee> expectedEmployees = of(secondEmploy);

        ResponseEntity<List<Employee>> result = employeeController.getEmployees(FEMALE, null, null);

        assertEquals(OK, result.getStatusCode());
        assertEquals(expectedEmployees, result.getBody());
    }

    @Test
    void should_return_updated_employee_when_update_an_employee_by_id() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);

        ResponseEntity<Employee> result = employeeController.updateEmployeeById(1, secondEmploy);

        assertEquals(OK, result.getStatusCode());
        assertEquals(secondEmploy, result.getBody());
    }

    @Test
    void should_return_not_found_when_update_an_non_existing_employee() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);

        ResponseEntity<Employee> result = employeeController.updateEmployeeById(2, secondEmploy);

        assertEquals(NOT_FOUND, result.getStatusCode());
    }

    @Test
    void should_delete_target_employee_when_delete_an_employee_with_specified_id() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);

        ResponseEntity<Employee> result = employeeController.deleteById(1);

        assertEquals(OK, result.getStatusCode());
        assertEquals(0, employeeController.getEmployees(null, null, null).getBody().size());
    }

    @Test
    void should_return_not_found_when_delete_an_non_existing_employee() throws URISyntaxException {
        employeeController.addEmployee(firstEmploy);

        ResponseEntity<Employee> result = employeeController.deleteById(2);

        assertEquals(NOT_FOUND, result.getStatusCode());
        assertEquals(1, employeeController.getEmployees(null, null, null).getBody().size());
    }
}
