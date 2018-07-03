package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.VMHostCluster;
import com.ltq.jamep.repository.VMHostClusterRepository;
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
 * REST controller for managing VMHostCluster.
 */
@RestController
@RequestMapping("/api")
public class VMHostClusterResource {

    private final Logger log = LoggerFactory.getLogger(VMHostClusterResource.class);

    private static final String ENTITY_NAME = "vMHostCluster";

    private final VMHostClusterRepository vMHostClusterRepository;

    public VMHostClusterResource(VMHostClusterRepository vMHostClusterRepository) {
        this.vMHostClusterRepository = vMHostClusterRepository;
    }

    /**
     * POST  /vm-host-clusters : Create a new vMHostCluster.
     *
     * @param vMHostCluster the vMHostCluster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vMHostCluster, or with status 400 (Bad Request) if the vMHostCluster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vm-host-clusters")
    @Timed
    public ResponseEntity<VMHostCluster> createVMHostCluster(@RequestBody VMHostCluster vMHostCluster) throws URISyntaxException {
        log.debug("REST request to save VMHostCluster : {}", vMHostCluster);
        if (vMHostCluster.getId() != null) {
            throw new BadRequestAlertException("A new vMHostCluster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VMHostCluster result = vMHostClusterRepository.save(vMHostCluster);
        return ResponseEntity.created(new URI("/api/vm-host-clusters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vm-host-clusters : Updates an existing vMHostCluster.
     *
     * @param vMHostCluster the vMHostCluster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vMHostCluster,
     * or with status 400 (Bad Request) if the vMHostCluster is not valid,
     * or with status 500 (Internal Server Error) if the vMHostCluster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vm-host-clusters")
    @Timed
    public ResponseEntity<VMHostCluster> updateVMHostCluster(@RequestBody VMHostCluster vMHostCluster) throws URISyntaxException {
        log.debug("REST request to update VMHostCluster : {}", vMHostCluster);
        if (vMHostCluster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VMHostCluster result = vMHostClusterRepository.save(vMHostCluster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vMHostCluster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vm-host-clusters : get all the vMHostClusters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vMHostClusters in body
     */
    @GetMapping("/vm-host-clusters")
    @Timed
    public List<VMHostCluster> getAllVMHostClusters() {
        log.debug("REST request to get all VMHostClusters");
        return vMHostClusterRepository.findAll();
    }

    /**
     * GET  /vm-host-clusters/:id : get the "id" vMHostCluster.
     *
     * @param id the id of the vMHostCluster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vMHostCluster, or with status 404 (Not Found)
     */
    @GetMapping("/vm-host-clusters/{id}")
    @Timed
    public ResponseEntity<VMHostCluster> getVMHostCluster(@PathVariable Long id) {
        log.debug("REST request to get VMHostCluster : {}", id);
        Optional<VMHostCluster> vMHostCluster = vMHostClusterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vMHostCluster);
    }

    /**
     * DELETE  /vm-host-clusters/:id : delete the "id" vMHostCluster.
     *
     * @param id the id of the vMHostCluster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vm-host-clusters/{id}")
    @Timed
    public ResponseEntity<Void> deleteVMHostCluster(@PathVariable Long id) {
        log.debug("REST request to delete VMHostCluster : {}", id);

        vMHostClusterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
