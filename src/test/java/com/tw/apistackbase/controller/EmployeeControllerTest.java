package com.tw.apistackbase.controller;

import com.tw.apistackbase.dto.Employee;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.collect.ImmutableList.of;
import static com.tw.apistackbase.dto.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController = new EmployeeController();

    @Test
    void should_return_paged_employee_succeed() {
        Employee firstEmploy = new Employee(1, "first", 18, MALE, 1000);
        Employee secondEmploy = new Employee(2, "first", 18, MALE, 1000);
        Employee thirdEmploy = new Employee(3, "first", 18, MALE, 1000);
        employeeController.add(firstEmploy);
        employeeController.add(secondEmploy);
        employeeController.add(thirdEmploy);
        List<Employee> firstPageEmployees = of(firstEmploy, secondEmploy);
        List<Employee> secondPageEmployees = of(thirdEmploy);

        List<Employee> firstQueryResult = employeeController.selectAll(1, 2);
        List<Employee> secondQueryResult = employeeController.selectAll(2, 2);

        assertEquals(firstPageEmployees, firstQueryResult);
        assertEquals(secondPageEmployees, secondQueryResult);
    }

    @Test
    void should_return_NoSuchElementException_when_page_exceed() {
        Employee firstEmploy = new Employee(1, "first", 18, MALE, 1000);
        employeeController.add(firstEmploy);

        assertThrows(NoSuchElementException.class, () -> employeeController.selectAll(2, 2));
    }
}
