package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.VCenter;
import com.ltq.jamep.repository.VCenterRepository;
import com.ltq.jamep.web.rest.errors.BadRequestAlertException;
import com.ltq.jamep.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VCenter.
 */
@RestController
@RequestMapping("/api")
public class VCenterResource {

    private final Logger log = LoggerFactory.getLogger(VCenterResource.class);

    private static final String ENTITY_NAME = "vCenter";

    private final VCenterRepository vCenterRepository;

    public VCenterResource(VCenterRepository vCenterRepository) {
        this.vCenterRepository = vCenterRepository;
    }

    /**
     * POST  /v-centers : Create a new vCenter.
     *
     * @param vCenter the vCenter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vCenter, or with status 400 (Bad Request) if the vCenter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/v-centers")
    @Timed
    public ResponseEntity<VCenter> createVCenter(@Valid @RequestBody VCenter vCenter) throws URISyntaxException {
        log.debug("REST request to save VCenter : {}", vCenter);
        if (vCenter.getId() != null) {
            throw new BadRequestAlertException("A new vCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VCenter result = vCenterRepository.save(vCenter);
        return ResponseEntity.created(new URI("/api/v-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /v-centers : Updates an existing vCenter.
     *
     * @param vCenter the vCenter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vCenter,
     * or with status 400 (Bad Request) if the vCenter is not valid,
     * or with status 500 (Internal Server Error) if the vCenter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/v-centers")
    @Timed
    public ResponseEntity<VCenter> updateVCenter(@Valid @RequestBody VCenter vCenter) throws URISyntaxException {
        log.debug("REST request to update VCenter : {}", vCenter);
        if (vCenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VCenter result = vCenterRepository.save(vCenter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vCenter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /v-centers : get all the vCenters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vCenters in body
     */
    @GetMapping("/v-centers")
    @Timed
    public List<VCenter> getAllVCenters() {
        log.debug("REST request to get all VCenters");
        return vCenterRepository.findAll();
    }

    /**
     * GET  /v-centers/:id : get the "id" vCenter.
     *
     * @param id the id of the vCenter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vCenter, or with status 404 (Not Found)
     */
    @GetMapping("/v-centers/{id}")
    @Timed
    public ResponseEntity<VCenter> getVCenter(@PathVariable Long id) {
        log.debug("REST request to get VCenter : {}", id);
        Optional<VCenter> vCenter = vCenterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vCenter);
    }

    /**
     * DELETE  /v-centers/:id : delete the "id" vCenter.
     *
     * @param id the id of the vCenter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/v-centers/{id}")
    @Timed
    public ResponseEntity<Void> deleteVCenter(@PathVariable Long id) {
        log.debug("REST request to delete VCenter : {}", id);

        vCenterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
