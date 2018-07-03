package com.ltq.jamep.repository;

import com.ltq.jamep.domain.Datastore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Datastore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatastoreRepository extends JpaRepository<Datastore, Long> {

    @Query(value = "select distinct datastore from Datastore datastore left join fetch datastore.virtualvolumes",
        countQuery = "select count(distinct datastore) from Datastore datastore")
    Page<Datastore> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct datastore from Datastore datastore left join fetch datastore.virtualvolumes")
    List<Datastore> findAllWithEagerRelationships();

    @Query("select datastore from Datastore datastore left join fetch datastore.virtualvolumes where datastore.id =:id")
    Optional<Datastore> findOneWithEagerRelationships(@Param("id") Long id);

}
