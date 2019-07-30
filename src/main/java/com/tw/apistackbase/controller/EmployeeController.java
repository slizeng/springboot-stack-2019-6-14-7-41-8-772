package com.tw.apistackbase.controller;

import com.google.common.collect.Lists;
import com.tw.apistackbase.dto.Employee;
import com.tw.apistackbase.dto.Gender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/employees", produces = "application/json")
public class EmployeeController {
    private List<Employee> employeeRepository = new ArrayList<>();

    @GetMapping()
    public ResponseEntity<List<Employee>> getEmployees(
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {

        List<Employee> filteredEmployees = ofNullable(gender)
                .map(theGender -> employeeRepository.stream()
                        .filter(employee -> employee.getGender().equals(theGender))
                        .collect(toList())
                ).orElseGet(() -> employeeRepository);

        if (nonNull(page) && nonNull(pageSize)) {
            List<List<Employee>> pagedEmployees = Lists.partition(filteredEmployees, pageSize);

            if (page >= pagedEmployees.size()) {
                return ResponseEntity.badRequest().build();
            }

            filteredEmployees = pagedEmployees.get(page - 1);
        }

        return ResponseEntity.ok(filteredEmployees);
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity<Employee> select(@PathVariable int id) {
        return employeeRepository.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Employee> add(@RequestBody Employee employee) throws URISyntaxException {
        employeeRepository.add(employee);

        return ResponseEntity.created(new URI("/employees")).body(employee);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Employee> update(@PathVariable int id, @RequestBody Employee newEmployee) {
        OptionalInt optionalIndex = IntStream.range(0, employeeRepository.size())
                .filter(index -> employeeRepository.get(index).getId() == id)
                .findFirst();

        if (optionalIndex.isPresent()) {
            employeeRepository.set(optionalIndex.getAsInt(), newEmployee);
            return ResponseEntity.ok(newEmployee);
        }

        return ResponseEntity.notFound().build();
    }
}
