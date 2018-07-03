package com.ltq.jamep.repository;

import com.ltq.jamep.domain.VirtualVolume;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VirtualVolume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VirtualVolumeRepository extends JpaRepository<VirtualVolume, Long> {

}
