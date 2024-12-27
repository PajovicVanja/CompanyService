package org.soa.companyService.controller;

import org.soa.companyService.dto.CreateServiceRequest;
import org.soa.companyService.dto.UpdateServiceRequest;
import org.soa.companyService.model.Company;
import org.soa.companyService.model.ServiceCategory;
import org.soa.companyService.model.ServiceM;
import org.soa.companyService.service.CompanyService;
import org.soa.companyService.service.ServiceCategoryService;
import org.soa.companyService.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<ServiceM> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceM> getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceM> createService(@RequestBody CreateServiceRequest request) {
        ServiceM service = new ServiceM();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setIdPicture(request.getIdPicture());
        service.setDuration(request.getDuration());

        // Fetch and set category
        ServiceCategory category = serviceCategoryService.getServiceCategoryById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("ServiceCategory not found with id " + request.getCategoryId()));
        service.setCategory(category);

        // Fetch and set company
        Company company = companyService.getCompanyById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id " + request.getCompanyId()));
        service.setCompany(company);

        ServiceM createdService = serviceService.createService(service);
        return ResponseEntity.ok(createdService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceM> updateService(@PathVariable Long id, @RequestBody UpdateServiceRequest request) {
        try {
            ServiceM service = new ServiceM();
            service.setName(request.getName());
            service.setDescription(request.getDescription());
            service.setPrice(request.getPrice());
            service.setIdPicture(request.getIdPicture());
            service.setDuration(request.getDuration());

            // Fetch and set category
            ServiceCategory category = serviceCategoryService.getServiceCategoryById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("ServiceCategory not found with id " + request.getCategoryId()));
            service.setCategory(category);

            // Fetch and set company
            Company company = companyService.getCompanyById(request.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with id " + request.getCompanyId()));
            service.setCompany(company);

            ServiceM updatedService = serviceService.updateService(id, service);
            return ResponseEntity.ok(updatedService);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
