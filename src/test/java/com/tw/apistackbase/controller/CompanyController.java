package com.tw.apistackbase.controller;

import com.tw.apistackbase.dto.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
        return companies.stream()
                .filter(company -> company.getId() ==id)
                .findFirst()
                .map(company -> ResponseEntity.ok().body(company))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Company> addCompany(@RequestBody Company company) throws URISyntaxException {
        companies.add(company);

        return ResponseEntity.created(new URI("/companies")).body(company);
    }
}
