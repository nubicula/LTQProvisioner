package com.ltq.jamep.repository;

import com.ltq.jamep.domain.VCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VCenterRepository extends JpaRepository<VCenter, Long> {

}
