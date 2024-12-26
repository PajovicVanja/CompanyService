package org.soa.companyService.controller;

import org.soa.companyService.model.ServiceM;
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

    // Get all services
    @GetMapping
    public List<ServiceM> getAllServices() {
        return serviceService.getAllServices();
    }

    // Get a service by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceM> getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new service
    @PostMapping
    public ServiceM createService(@RequestBody ServiceM service) {
        return serviceService.createService(service);
    }

    // Update a service
    @PutMapping("/{id}")
    public ResponseEntity<ServiceM> updateService(@PathVariable Long id, @RequestBody ServiceM service) {
        try {
            return ResponseEntity.ok(serviceService.updateService(id, service));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
