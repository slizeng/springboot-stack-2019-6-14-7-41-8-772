package com.tw.apistackbase.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tw.apistackbase.entity.Employee;
import com.tw.apistackbase.entity.GENDER;
import com.tw.apistackbase.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class EmployeeDao {
    private List<Employee> employeeRepository = new ArrayList<>();

    public Employee add(Employee employee) {
        employeeRepository.add(employee);

        return employee;
    }

    public List<Employee> getAll() {
        return ImmutableList.copyOf(employeeRepository);
    }

    public Employee get(int id) {
        return employeeRepository.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getAll(int page, int pageSize) {
        List<Employee> employees = getAll();
        List<List<Employee>> pagedEmployees = Lists.partition(employees, pageSize);

        if (page > pagedEmployees.size()) {
            throw new NoSuchElementException();
        }

        return pagedEmployees.get(page - 1);
    }

    public List<Employee> getAll(GENDER gender) {
        return employeeRepository.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }
}
