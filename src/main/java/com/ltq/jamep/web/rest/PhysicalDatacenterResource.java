package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.PhysicalDatacenter;
import com.ltq.jamep.repository.PhysicalDatacenterRepository;
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
 * REST controller for managing PhysicalDatacenter.
 */
@RestController
@RequestMapping("/api")
public class PhysicalDatacenterResource {

    private final Logger log = LoggerFactory.getLogger(PhysicalDatacenterResource.class);

    private static final String ENTITY_NAME = "physicalDatacenter";

    private final PhysicalDatacenterRepository physicalDatacenterRepository;

    public PhysicalDatacenterResource(PhysicalDatacenterRepository physicalDatacenterRepository) {
        this.physicalDatacenterRepository = physicalDatacenterRepository;
    }

    /**
     * POST  /physical-datacenters : Create a new physicalDatacenter.
     *
     * @param physicalDatacenter the physicalDatacenter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new physicalDatacenter, or with status 400 (Bad Request) if the physicalDatacenter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/physical-datacenters")
    @Timed
    public ResponseEntity<PhysicalDatacenter> createPhysicalDatacenter(@RequestBody PhysicalDatacenter physicalDatacenter) throws URISyntaxException {
        log.debug("REST request to save PhysicalDatacenter : {}", physicalDatacenter);
        if (physicalDatacenter.getId() != null) {
            throw new BadRequestAlertException("A new physicalDatacenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhysicalDatacenter result = physicalDatacenterRepository.save(physicalDatacenter);
        return ResponseEntity.created(new URI("/api/physical-datacenters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /physical-datacenters : Updates an existing physicalDatacenter.
     *
     * @param physicalDatacenter the physicalDatacenter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated physicalDatacenter,
     * or with status 400 (Bad Request) if the physicalDatacenter is not valid,
     * or with status 500 (Internal Server Error) if the physicalDatacenter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/physical-datacenters")
    @Timed
    public ResponseEntity<PhysicalDatacenter> updatePhysicalDatacenter(@RequestBody PhysicalDatacenter physicalDatacenter) throws URISyntaxException {
        log.debug("REST request to update PhysicalDatacenter : {}", physicalDatacenter);
        if (physicalDatacenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhysicalDatacenter result = physicalDatacenterRepository.save(physicalDatacenter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, physicalDatacenter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /physical-datacenters : get all the physicalDatacenters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of physicalDatacenters in body
     */
    @GetMapping("/physical-datacenters")
    @Timed
    public List<PhysicalDatacenter> getAllPhysicalDatacenters() {
        log.debug("REST request to get all PhysicalDatacenters");
        return physicalDatacenterRepository.findAll();
    }

    /**
     * GET  /physical-datacenters/:id : get the "id" physicalDatacenter.
     *
     * @param id the id of the physicalDatacenter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the physicalDatacenter, or with status 404 (Not Found)
     */
    @GetMapping("/physical-datacenters/{id}")
    @Timed
    public ResponseEntity<PhysicalDatacenter> getPhysicalDatacenter(@PathVariable Long id) {
        log.debug("REST request to get PhysicalDatacenter : {}", id);
        Optional<PhysicalDatacenter> physicalDatacenter = physicalDatacenterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(physicalDatacenter);
    }

    /**
     * DELETE  /physical-datacenters/:id : delete the "id" physicalDatacenter.
     *
     * @param id the id of the physicalDatacenter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/physical-datacenters/{id}")
    @Timed
    public ResponseEntity<Void> deletePhysicalDatacenter(@PathVariable Long id) {
        log.debug("REST request to delete PhysicalDatacenter : {}", id);

        physicalDatacenterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
