package com.tw.apistackbase.controller;

import com.google.common.collect.Lists;
import com.tw.apistackbase.dto.Company;
import com.tw.apistackbase.dto.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/companies", produces = "application/json")
public class CompanyController {

    private List<Company> companies = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companies);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable int id) {
        return selectCompanyById(id)
                .map(company -> ResponseEntity.ok().body(company))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Company> addCompany(@RequestBody Company company) throws URISyntaxException {
        companies.add(company);

        return ResponseEntity.created(new URI("/companies")).body(company);
    }

    @GetMapping(path = "/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployees(@PathVariable int id) {
        return selectCompanyById(id)
                .map(company -> ResponseEntity.ok().body(company.getEmployees()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllWithPagination(
            @PathVariable Integer page, @PathVariable Integer pageSize) {

        List<List<Company>> pagedCompanies = Lists.partition(companies, pageSize);

        return ResponseEntity.ok().body(pagedCompanies.get(page - 1));
    }

    private Optional<Company> selectCompanyById(@PathVariable int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst();
    }
}
