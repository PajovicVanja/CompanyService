package org.soa.companyService.repository;

import org.soa.companyService.model.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
}



