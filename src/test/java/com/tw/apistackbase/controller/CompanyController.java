package com.tw.apistackbase.controller;

import com.tw.apistackbase.dto.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public ResponseEntity<Company> addCompany(Company company) throws URISyntaxException {
        return ResponseEntity.created(new URI("/companies")).body(company);
    }
}
