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
import java.util.stream.IntStream;

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

        if (page > companies.size()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(pagedCompanies.get(page - 1));
    }

    public ResponseEntity<Company> updateCompany(int id, Company newCompany) {
        return IntStream.range(0, companies.size())
                .boxed()
                .filter(index -> companies.get(index).getId() == id)
                .findFirst()
                .map(index -> {
                    companies.set(index, newCompany);
                    return ResponseEntity.ok().body(newCompany);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private Optional<Company> selectCompanyById(@PathVariable int id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst();
    }

    public ResponseEntity<Company> deleteCompany(int id) {
        return IntStream.range(0, companies.size())
                .boxed()
                .filter(index -> companies.get(index).getId() == id)
                .findFirst()
                .map(index -> ResponseEntity.ok().body(companies.remove((int) index)))
                .orElse(ResponseEntity.notFound().build());
    }
}
