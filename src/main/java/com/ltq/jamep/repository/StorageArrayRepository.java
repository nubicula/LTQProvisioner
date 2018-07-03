package com.ltq.jamep.repository;

import com.ltq.jamep.domain.StorageArray;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StorageArray entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorageArrayRepository extends JpaRepository<StorageArray, Long> {

}
