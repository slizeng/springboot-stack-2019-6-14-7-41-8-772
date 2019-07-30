package com.tw.apistackbase.service;

import com.google.common.collect.ImmutableList;
import com.tw.apistackbase.repository.EmployeeRepository;
import com.tw.apistackbase.dto.Employee;
import com.tw.apistackbase.exception.CannotAddEmployeeException;
import com.tw.apistackbase.exception.EmployeeNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.collect.ImmutableList.of;
import static com.tw.apistackbase.dto.GENDER.female;
import static com.tw.apistackbase.dto.GENDER.male;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee = new Employee(1, "name", 18, male, 10000);

    @Test
    public void should_return_employees_as_list_when_get_all_employees_succeed() {
        List<Employee> employeeEntities = of(employee);
        when(employeeRepository.selectAll()).thenReturn(employeeEntities);

        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertEquals(employeeEntities, allEmployees);
    }

    @Test
    public void should_return_employee_when_add_an_employee_succeed() {
        when(employeeRepository.add(employee)).thenReturn(employee);

        Employee returnedEmployee = employeeService.addEmployee(employee);

        assertEquals(employee, returnedEmployee);
    }

    @Test
    public void should_throw_CannotAddEmployeeException_when_add_an_employee_failed() {
        when(employeeRepository.add(employee)).thenThrow(new RuntimeException());

        assertThrows(CannotAddEmployeeException.class, () -> employeeService.addEmployee(employee));
    }

    @Test
    public void should_throw_CannotAddEmployeeException_when_add_an_employee_and_dao_return_null() {
        when(employeeRepository.add(employee)).thenReturn(null);

        assertThrows(CannotAddEmployeeException.class, () -> employeeService.addEmployee(employee));
    }

    @Test
    public void should_get_target_employee_when_query_a_certain_employee_succeed() {
        when(employeeRepository.select(1)).thenReturn(employee);

        Employee returnedEmploy = employeeService.getCertainEmployee(1);

        assertEquals(employee, returnedEmploy);
    }

    @Test
    public void should_throw_EmployeeNotFoundException_when_query_an_employee_but_an_exception_thrown_from_dao() {
        when(employeeRepository.select(2)).thenThrow(new EmployeeNotFoundException());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getCertainEmployee(2));
    }

    @Test
    public void should_return_target_employees_when_query_employees_with_paging_succeed() {
        Employee firstEmploy = new Employee(1, "first", 18, male, 1000);
        Employee secondEmploy = new Employee(2, "first", 18, male, 1000);
        Employee thirdEmploy = new Employee(3, "first", 18, male, 1000);
        List<Employee> firstPageEmployees = of(firstEmploy, secondEmploy);
        List<Employee> secondPageEmployees = of(thirdEmploy);

        when(employeeRepository.selectAll(1, 2)).thenReturn(firstPageEmployees);
        when(employeeRepository.selectAll(2, 2)).thenReturn(secondPageEmployees);

        List<Employee> firstPageQueryResult = employeeService.getPagedEmployees(1, 2);
        List<Employee> secondPageQueryResult = employeeService.getPagedEmployees(2, 2);

        assertEquals(firstPageEmployees, firstPageQueryResult);
        assertEquals(secondPageEmployees, secondPageQueryResult);
    }

    @Test
    public void should_throws_NoSuchExceptions_when_query_paged_employees_and_page_is_exceed() {
        when(employeeRepository.selectAll(2, 2)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> employeeService.getPagedEmployees(2, 2));
    }

    @Test
    public void should_return_filtered_employees_when_query_employee_by_gender() {
        ImmutableList<Employee> expectedFilteredEmployees = of(employee);
        when(employeeRepository.selectAll(male)).thenReturn(expectedFilteredEmployees);

        List<Employee> returnEmployees = employeeService.getEmployeesByGender(male);

        assertEquals(expectedFilteredEmployees, returnEmployees);
    }

    @Test
    public void should_return_updated_employee_when_update_an_employee_succeed() {
        Employee  newEmployee = new Employee(1, "name", 18, female, 1000);
        when(employeeRepository.update(1, newEmployee)).thenReturn(newEmployee);

        Employee returnedEmployed = employeeService.updateEmploy(1, newEmployee);

        assertEquals(newEmployee, returnedEmployed);
    }
}
