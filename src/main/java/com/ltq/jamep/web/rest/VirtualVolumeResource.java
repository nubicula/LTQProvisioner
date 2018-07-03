package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.VirtualVolume;
import com.ltq.jamep.repository.VirtualVolumeRepository;
import com.ltq.jamep.web.rest.errors.BadRequestAlertException;
import com.ltq.jamep.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VirtualVolume.
 */
@RestController
@RequestMapping("/api")
public class VirtualVolumeResource {

    private final Logger log = LoggerFactory.getLogger(VirtualVolumeResource.class);

    private static final String ENTITY_NAME = "virtualVolume";

    private final VirtualVolumeRepository virtualVolumeRepository;

    public VirtualVolumeResource(VirtualVolumeRepository virtualVolumeRepository) {
        this.virtualVolumeRepository = virtualVolumeRepository;
    }

    /**
     * POST  /virtual-volumes : Create a new virtualVolume.
     *
     * @param virtualVolume the virtualVolume to create
     * @return the ResponseEntity with status 201 (Created) and with body the new virtualVolume, or with status 400 (Bad Request) if the virtualVolume has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/virtual-volumes")
    @Timed
    public ResponseEntity<VirtualVolume> createVirtualVolume(@RequestBody VirtualVolume virtualVolume) throws URISyntaxException {
        log.debug("REST request to save VirtualVolume : {}", virtualVolume);
        if (virtualVolume.getId() != null) {
            throw new BadRequestAlertException("A new virtualVolume cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VirtualVolume result = virtualVolumeRepository.save(virtualVolume);
        return ResponseEntity.created(new URI("/api/virtual-volumes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /virtual-volumes : Updates an existing virtualVolume.
     *
     * @param virtualVolume the virtualVolume to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated virtualVolume,
     * or with status 400 (Bad Request) if the virtualVolume is not valid,
     * or with status 500 (Internal Server Error) if the virtualVolume couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/virtual-volumes")
    @Timed
    public ResponseEntity<VirtualVolume> updateVirtualVolume(@RequestBody VirtualVolume virtualVolume) throws URISyntaxException {
        log.debug("REST request to update VirtualVolume : {}", virtualVolume);
        if (virtualVolume.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VirtualVolume result = virtualVolumeRepository.save(virtualVolume);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, virtualVolume.getId().toString()))
            .body(result);
    }

    /**
     * GET  /virtual-volumes : get all the virtualVolumes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of virtualVolumes in body
     */
    @GetMapping("/virtual-volumes")
    @Timed
    public List<VirtualVolume> getAllVirtualVolumes() {
        log.debug("REST request to get all VirtualVolumes");
        return virtualVolumeRepository.findAll();
    }

    /**
     * GET  /virtual-volumes/:id : get the "id" virtualVolume.
     *
     * @param id the id of the virtualVolume to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the virtualVolume, or with status 404 (Not Found)
     */
    @GetMapping("/virtual-volumes/{id}")
    @Timed
    public ResponseEntity<VirtualVolume> getVirtualVolume(@PathVariable Long id) {
        log.debug("REST request to get VirtualVolume : {}", id);
        Optional<VirtualVolume> virtualVolume = virtualVolumeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(virtualVolume);
    }

    /**
     * DELETE  /virtual-volumes/:id : delete the "id" virtualVolume.
     *
     * @param id the id of the virtualVolume to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/virtual-volumes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVirtualVolume(@PathVariable Long id) {
        log.debug("REST request to delete VirtualVolume : {}", id);

        virtualVolumeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
