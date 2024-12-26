package org.soa.companyService.controller;

import org.soa.companyService.model.ServiceCategory;
import org.soa.companyService.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-categories")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    // Get all service categories
    @GetMapping
    public List<ServiceCategory> getAllServiceCategories() {
        return serviceCategoryService.getAllServiceCategories();
    }

    // Get a service category by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategory> getServiceCategoryById(@PathVariable Long id) {
        return serviceCategoryService.getServiceCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new service category
    @PostMapping
    public ServiceCategory createServiceCategory(@RequestBody ServiceCategory serviceCategory) {
        return serviceCategoryService.createServiceCategory(serviceCategory);
    }

    // Update a service category
    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategory> updateServiceCategory(@PathVariable Long id, @RequestBody ServiceCategory serviceCategory) {
        try {
            return ResponseEntity.ok(serviceCategoryService.updateServiceCategory(id, serviceCategory));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a service category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.noContent().build();
    }
}
