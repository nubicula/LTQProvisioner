package com.ltq.jamep.repository;

import com.ltq.jamep.domain.VMHost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the VMHost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VMHostRepository extends JpaRepository<VMHost, Long> {

    @Query(value = "select distinct vm_host from VMHost vm_host left join fetch vm_host.datastores",
        countQuery = "select count(distinct vm_host) from VMHost vm_host")
    Page<VMHost> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct vm_host from VMHost vm_host left join fetch vm_host.datastores")
    List<VMHost> findAllWithEagerRelationships();

    @Query("select vm_host from VMHost vm_host left join fetch vm_host.datastores where vm_host.id =:id")
    Optional<VMHost> findOneWithEagerRelationships(@Param("id") Long id);

}
