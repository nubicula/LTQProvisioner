package com.ltq.jamep.repository;

import com.ltq.jamep.domain.Vcenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vcenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VcenterRepository extends JpaRepository<Vcenter, Long> {

}
