package com.ltq.jamep.repository;

import com.ltq.jamep.domain.VMHostCluster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VMHostCluster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VMHostClusterRepository extends JpaRepository<VMHostCluster, Long> {

}
