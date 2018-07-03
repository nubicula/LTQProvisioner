package com.ltq.jamep.repository;

import com.ltq.jamep.domain.PhysicalDatacenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PhysicalDatacenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhysicalDatacenterRepository extends JpaRepository<PhysicalDatacenter, Long> {

}
