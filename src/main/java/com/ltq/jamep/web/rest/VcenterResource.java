package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.Vcenter;
import com.ltq.jamep.repository.VcenterRepository;
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
 * REST controller for managing Vcenter.
 */
@RestController
@RequestMapping("/api")
public class VcenterResource {

    private final Logger log = LoggerFactory.getLogger(VcenterResource.class);

    private static final String ENTITY_NAME = "vcenter";

    private final VcenterRepository vcenterRepository;

    public VcenterResource(VcenterRepository vcenterRepository) {
        this.vcenterRepository = vcenterRepository;
    }

    /**
     * POST  /vcenters : Create a new vcenter.
     *
     * @param vcenter the vcenter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vcenter, or with status 400 (Bad Request) if the vcenter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vcenters")
    @Timed
    public ResponseEntity<Vcenter> createVcenter(@Valid @RequestBody Vcenter vcenter) throws URISyntaxException {
        log.debug("REST request to save Vcenter : {}", vcenter);
        if (vcenter.getId() != null) {
            throw new BadRequestAlertException("A new vcenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vcenter result = vcenterRepository.save(vcenter);
        return ResponseEntity.created(new URI("/api/vcenters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vcenters : Updates an existing vcenter.
     *
     * @param vcenter the vcenter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vcenter,
     * or with status 400 (Bad Request) if the vcenter is not valid,
     * or with status 500 (Internal Server Error) if the vcenter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vcenters")
    @Timed
    public ResponseEntity<Vcenter> updateVcenter(@Valid @RequestBody Vcenter vcenter) throws URISyntaxException {
        log.debug("REST request to update Vcenter : {}", vcenter);
        if (vcenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vcenter result = vcenterRepository.save(vcenter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vcenter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vcenters : get all the vcenters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vcenters in body
     */
    @GetMapping("/vcenters")
    @Timed
    public List<Vcenter> getAllVcenters() {
        log.debug("REST request to get all Vcenters");
        return vcenterRepository.findAll();
    }

    /**
     * GET  /vcenters/:id : get the "id" vcenter.
     *
     * @param id the id of the vcenter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vcenter, or with status 404 (Not Found)
     */
    @GetMapping("/vcenters/{id}")
    @Timed
    public ResponseEntity<Vcenter> getVcenter(@PathVariable Long id) {
        log.debug("REST request to get Vcenter : {}", id);
        Optional<Vcenter> vcenter = vcenterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vcenter);
    }

    /**
     * DELETE  /vcenters/:id : delete the "id" vcenter.
     *
     * @param id the id of the vcenter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vcenters/{id}")
    @Timed
    public ResponseEntity<Void> deleteVcenter(@PathVariable Long id) {
        log.debug("REST request to delete Vcenter : {}", id);

        vcenterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
