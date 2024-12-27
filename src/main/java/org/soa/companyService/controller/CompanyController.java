package org.soa.companyService.controller;

import org.soa.companyService.dto.CreateCompanyRequest;
import org.soa.companyService.dto.UpdateCompanyRequest;
import org.soa.companyService.model.Company;
import org.soa.companyService.model.Location;
import org.soa.companyService.model.SmsNotificationConfig;
import org.soa.companyService.service.CompanyService;
import org.soa.companyService.service.LocationService;
import org.soa.companyService.service.SmsNotificationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SmsNotificationConfigService smsNotificationConfigService;

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CreateCompanyRequest request) {
        // Map DTO to entity
        Company company = new Company();
        company.setDescription(request.getDescription());
        company.setIdPicture(request.getIdPicture());
        company.setIdAuthUser(request.getIdAuthUser());
        company.setUuidUrl(request.getUuidUrl());
        company.setAddress(request.getAddress());
        company.setPhoneNumber(request.getPhoneNumber());
        company.setEmail(request.getEmail());
        company.setFirstName(request.getFirstName());
        company.setLastName(request.getLastName());
        company.setCompanyName(request.getCompanyName());

        // Fetch and set Location
        Location location = locationService.getLocationById(request.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found with id " + request.getLocationId()));
        company.setLocation(location);

        // Fetch and set SmsNotificationConfig
        SmsNotificationConfig smsNotificationConfig = smsNotificationConfigService
                .getSmsNotificationConfigById(request.getSmsNotificationConfigId())
                .orElseThrow(() -> new RuntimeException("SmsNotificationConfig not found with id " + request.getSmsNotificationConfigId()));
        company.setSmsNotificationConfig(smsNotificationConfig);

        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody UpdateCompanyRequest request) {
        try {
            // Map DTO to entity
            Company company = new Company();
            company.setDescription(request.getDescription());
            company.setIdPicture(request.getIdPicture());
            company.setAddress(request.getAddress());
            company.setPhoneNumber(request.getPhoneNumber());
            company.setEmail(request.getEmail());
            company.setFirstName(request.getFirstName());
            company.setLastName(request.getLastName());
            company.setCompanyName(request.getCompanyName());

            // Fetch and set Location
            Location location = locationService.getLocationById(request.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found with id " + request.getLocationId()));
            company.setLocation(location);

            // Fetch and set SmsNotificationConfig
            SmsNotificationConfig smsNotificationConfig = smsNotificationConfigService
                    .getSmsNotificationConfigById(request.getSmsNotificationConfigId())
                    .orElseThrow(() -> new RuntimeException("SmsNotificationConfig not found with id " + request.getSmsNotificationConfigId()));
            company.setSmsNotificationConfig(smsNotificationConfig);

            Company updatedCompany = companyService.updateCompany(id, company);
            return ResponseEntity.ok(updatedCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
