package org.soa.companyService.controller;

import org.soa.companyService.dto.CreateServiceCategoryRequest;
import org.soa.companyService.dto.UpdateServiceCategoryRequest;
import org.soa.companyService.model.Company;
import org.soa.companyService.model.ServiceCategory;
import org.soa.companyService.service.CompanyService;
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

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<ServiceCategory> getAllServiceCategories() {
        return serviceCategoryService.getAllServiceCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategory> getServiceCategoryById(@PathVariable Long id) {
        return serviceCategoryService.getServiceCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceCategory> createServiceCategory(@RequestBody CreateServiceCategoryRequest request) {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName(request.getName());

        // Fetch and set Company
        Company company = companyService.getCompanyById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id " + request.getCompanyId()));
        serviceCategory.setCompany(company);

        ServiceCategory createdServiceCategory = serviceCategoryService.createServiceCategory(serviceCategory);
        return ResponseEntity.ok(createdServiceCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCategory> updateServiceCategory(@PathVariable Long id, @RequestBody UpdateServiceCategoryRequest request) {
        try {
            ServiceCategory serviceCategory = new ServiceCategory();
            serviceCategory.setName(request.getName());

            // Fetch and set Company
            Company company = companyService.getCompanyById(request.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with id " + request.getCompanyId()));
            serviceCategory.setCompany(company);

            ServiceCategory updatedServiceCategory = serviceCategoryService.updateServiceCategory(id, serviceCategory);
            return ResponseEntity.ok(updatedServiceCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceCategory(@PathVariable Long id) {
        serviceCategoryService.deleteServiceCategory(id);
        return ResponseEntity.noContent().build();
    }
}
