package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.VMHost;
import com.ltq.jamep.repository.VMHostRepository;
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
 * REST controller for managing VMHost.
 */
@RestController
@RequestMapping("/api")
public class VMHostResource {

    private final Logger log = LoggerFactory.getLogger(VMHostResource.class);

    private static final String ENTITY_NAME = "vMHost";

    private final VMHostRepository vMHostRepository;

    public VMHostResource(VMHostRepository vMHostRepository) {
        this.vMHostRepository = vMHostRepository;
    }

    /**
     * POST  /vm-hosts : Create a new vMHost.
     *
     * @param vMHost the vMHost to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vMHost, or with status 400 (Bad Request) if the vMHost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vm-hosts")
    @Timed
    public ResponseEntity<VMHost> createVMHost(@RequestBody VMHost vMHost) throws URISyntaxException {
        log.debug("REST request to save VMHost : {}", vMHost);
        if (vMHost.getId() != null) {
            throw new BadRequestAlertException("A new vMHost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VMHost result = vMHostRepository.save(vMHost);
        return ResponseEntity.created(new URI("/api/vm-hosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vm-hosts : Updates an existing vMHost.
     *
     * @param vMHost the vMHost to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vMHost,
     * or with status 400 (Bad Request) if the vMHost is not valid,
     * or with status 500 (Internal Server Error) if the vMHost couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vm-hosts")
    @Timed
    public ResponseEntity<VMHost> updateVMHost(@RequestBody VMHost vMHost) throws URISyntaxException {
        log.debug("REST request to update VMHost : {}", vMHost);
        if (vMHost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VMHost result = vMHostRepository.save(vMHost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vMHost.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vm-hosts : get all the vMHosts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of vMHosts in body
     */
    @GetMapping("/vm-hosts")
    @Timed
    public List<VMHost> getAllVMHosts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all VMHosts");
        return vMHostRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /vm-hosts/:id : get the "id" vMHost.
     *
     * @param id the id of the vMHost to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vMHost, or with status 404 (Not Found)
     */
    @GetMapping("/vm-hosts/{id}")
    @Timed
    public ResponseEntity<VMHost> getVMHost(@PathVariable Long id) {
        log.debug("REST request to get VMHost : {}", id);
        Optional<VMHost> vMHost = vMHostRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(vMHost);
    }

    /**
     * DELETE  /vm-hosts/:id : delete the "id" vMHost.
     *
     * @param id the id of the vMHost to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vm-hosts/{id}")
    @Timed
    public ResponseEntity<Void> deleteVMHost(@PathVariable Long id) {
        log.debug("REST request to delete VMHost : {}", id);

        vMHostRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
