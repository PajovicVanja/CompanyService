package org.soa.companyService.controller;

import org.soa.companyService.model.BusinessHours;
import org.soa.companyService.service.BusinessHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-hours")
public class BusinessHoursController {

    @Autowired
    private BusinessHoursService businessHoursService;

    // Get all business hours
    @GetMapping
    public List<BusinessHours> getAllBusinessHours() {
        return businessHoursService.getAllBusinessHours();
    }

    // Get business hours by ID
    @GetMapping("/{id}")
    public ResponseEntity<BusinessHours> getBusinessHoursById(@PathVariable Long id) {
        return businessHoursService.getBusinessHoursById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new business hours
    @PostMapping
    public BusinessHours createBusinessHours(@RequestBody BusinessHours businessHours) {
        return businessHoursService.createBusinessHours(businessHours);
    }

    // Update business hours
    @PutMapping("/{id}")
    public ResponseEntity<BusinessHours> updateBusinessHours(@PathVariable Long id, @RequestBody BusinessHours businessHours) {
        try {
            return ResponseEntity.ok(businessHoursService.updateBusinessHours(id, businessHours));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete business hours
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessHours(@PathVariable Long id) {
        businessHoursService.deleteBusinessHours(id);
        return ResponseEntity.noContent().build();
    }
}
