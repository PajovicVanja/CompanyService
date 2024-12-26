package org.soa.companyService.service;

import org.soa.companyService.model.Company;
import org.soa.companyService.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        return companyRepository.findById(id)
                .map(company -> {
                    // Update fields
                    company.setDescription(updatedCompany.getDescription());
                    company.setSmsNotificationConfig(updatedCompany.getSmsNotificationConfig());
                    company.setIdPicture(updatedCompany.getIdPicture());
                    company.setIdAuthUser(updatedCompany.getIdAuthUser());
                    company.setUuidUrl(updatedCompany.getUuidUrl());
                    company.setAddress(updatedCompany.getAddress());
                    company.setPhoneNumber(updatedCompany.getPhoneNumber());
                    company.setEmail(updatedCompany.getEmail());
                    company.setFirstName(updatedCompany.getFirstName());
                    company.setLastName(updatedCompany.getLastName());
                    company.setCompanyName(updatedCompany.getCompanyName());
                    company.setLocation(updatedCompany.getLocation());

                    // Save the updated company
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found with id " + id));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
