package org.soa.companyService.repository;

import org.soa.companyService.model.ServiceM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceM, Long> {
}
