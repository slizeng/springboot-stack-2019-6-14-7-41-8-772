package com.tw.apistackbase.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tw.apistackbase.dto.Employee;
import com.tw.apistackbase.dto.GENDER;
import com.tw.apistackbase.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeRepository = new ArrayList<>();

    public Employee add(Employee employee) {
        employeeRepository.add(employee);

        return employee;
    }

    public Employee select(int id) {
        return employeeRepository.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> selectAll() {
        return ImmutableList.copyOf(employeeRepository);
    }

    public List<Employee> selectAll(int page, int pageSize) {
        List<List<Employee>> pagedEmployees = Lists.partition(employeeRepository, pageSize);

        if (page > pagedEmployees.size()) {
            throw new NoSuchElementException();
        }

        return pagedEmployees.get(page - 1);
    }

    public List<Employee> selectAll(GENDER gender) {
        return employeeRepository.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee update(int id, Employee newEmployee) {
        OptionalInt optionalIndex = IntStream.range(0, employeeRepository.size())
                .filter(index -> employeeRepository.get(index).getId() == id)
                .findFirst();

        if (optionalIndex.isPresent()) {
            employeeRepository.set(optionalIndex.getAsInt(), newEmployee);
        }

        throw new NoSuchElementException();
    }
}
