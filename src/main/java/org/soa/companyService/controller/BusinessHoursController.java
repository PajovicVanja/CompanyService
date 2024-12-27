package org.soa.companyService.controller;

import org.soa.companyService.dto.CreateBusinessHoursRequest;
import org.soa.companyService.dto.UpdateBusinessHoursRequest;
import org.soa.companyService.model.BusinessHours;
import org.soa.companyService.model.Company;
import org.soa.companyService.service.BusinessHoursService;
import org.soa.companyService.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-hours")
public class BusinessHoursController {

    @Autowired
    private BusinessHoursService businessHoursService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<BusinessHours> getAllBusinessHours() {
        return businessHoursService.getAllBusinessHours();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessHours> getBusinessHoursById(@PathVariable Long id) {
        return businessHoursService.getBusinessHoursById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BusinessHours> createBusinessHours(@RequestBody CreateBusinessHoursRequest request) {
        BusinessHours businessHours = new BusinessHours();
        businessHours.setDayNumber(request.getDayNumber());
        businessHours.setTimeFrom(request.getTimeFrom());
        businessHours.setTimeTo(request.getTimeTo());
        businessHours.setPauseFrom(request.getPauseFrom());
        businessHours.setPauseTo(request.getPauseTo());
        businessHours.setDay(request.getDay());

        // Fetch and set Company
        Company company = companyService.getCompanyById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id " + request.getCompanyId()));
        businessHours.setCompany(company);

        BusinessHours createdBusinessHours = businessHoursService.createBusinessHours(businessHours);
        return ResponseEntity.ok(createdBusinessHours);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessHours> updateBusinessHours(@PathVariable Long id, @RequestBody UpdateBusinessHoursRequest request) {
        try {
            BusinessHours businessHours = new BusinessHours();
            businessHours.setDayNumber(request.getDayNumber());
            businessHours.setTimeFrom(request.getTimeFrom());
            businessHours.setTimeTo(request.getTimeTo());
            businessHours.setPauseFrom(request.getPauseFrom());
            businessHours.setPauseTo(request.getPauseTo());
            businessHours.setDay(request.getDay());

            // Fetch and set Company
            Company company = companyService.getCompanyById(request.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with id " + request.getCompanyId()));
            businessHours.setCompany(company);

            BusinessHours updatedBusinessHours = businessHoursService.updateBusinessHours(id, businessHours);
            return ResponseEntity.ok(updatedBusinessHours);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessHours(@PathVariable Long id) {
        businessHoursService.deleteBusinessHours(id);
        return ResponseEntity.noContent().build();
    }
}
