package com.ltq.jamep.repository;

import com.ltq.jamep.domain.DatastoreCluster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DatastoreCluster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatastoreClusterRepository extends JpaRepository<DatastoreCluster, Long> {

}
