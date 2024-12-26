package org.soa.companyService.service;

import org.soa.companyService.model.ServiceM;
import org.soa.companyService.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    // Get all services
    public List<ServiceM> getAllServices() {
        return serviceRepository.findAll();
    }

    // Get a service by ID
    public Optional<ServiceM> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    // Create a new service
    public ServiceM createService(ServiceM service) {
        return serviceRepository.save(service);
    }

    // Update an existing service
    public ServiceM updateService(Long id, ServiceM updatedService) {
        return serviceRepository.findById(id)
                .map(existingService -> {
                    existingService.setCategory(updatedService.getCategory());
                    existingService.setCompany(updatedService.getCompany());
                    existingService.setName(updatedService.getName());
                    existingService.setDescription(updatedService.getDescription());
                    existingService.setPrice(updatedService.getPrice());
                    existingService.setIdPicture(updatedService.getIdPicture());
                    existingService.setDuration(updatedService.getDuration());
                    return serviceRepository.save(existingService);
                })
                .orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    }

    // Delete a service by ID
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
