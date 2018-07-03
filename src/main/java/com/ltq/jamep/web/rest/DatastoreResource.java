package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.Datastore;
import com.ltq.jamep.repository.DatastoreRepository;
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
 * REST controller for managing Datastore.
 */
@RestController
@RequestMapping("/api")
public class DatastoreResource {

    private final Logger log = LoggerFactory.getLogger(DatastoreResource.class);

    private static final String ENTITY_NAME = "datastore";

    private final DatastoreRepository datastoreRepository;

    public DatastoreResource(DatastoreRepository datastoreRepository) {
        this.datastoreRepository = datastoreRepository;
    }

    /**
     * POST  /datastores : Create a new datastore.
     *
     * @param datastore the datastore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new datastore, or with status 400 (Bad Request) if the datastore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/datastores")
    @Timed
    public ResponseEntity<Datastore> createDatastore(@RequestBody Datastore datastore) throws URISyntaxException {
        log.debug("REST request to save Datastore : {}", datastore);
        if (datastore.getId() != null) {
            throw new BadRequestAlertException("A new datastore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Datastore result = datastoreRepository.save(datastore);
        return ResponseEntity.created(new URI("/api/datastores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /datastores : Updates an existing datastore.
     *
     * @param datastore the datastore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated datastore,
     * or with status 400 (Bad Request) if the datastore is not valid,
     * or with status 500 (Internal Server Error) if the datastore couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/datastores")
    @Timed
    public ResponseEntity<Datastore> updateDatastore(@RequestBody Datastore datastore) throws URISyntaxException {
        log.debug("REST request to update Datastore : {}", datastore);
        if (datastore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Datastore result = datastoreRepository.save(datastore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, datastore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /datastores : get all the datastores.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of datastores in body
     */
    @GetMapping("/datastores")
    @Timed
    public List<Datastore> getAllDatastores(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Datastores");
        return datastoreRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /datastores/:id : get the "id" datastore.
     *
     * @param id the id of the datastore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the datastore, or with status 404 (Not Found)
     */
    @GetMapping("/datastores/{id}")
    @Timed
    public ResponseEntity<Datastore> getDatastore(@PathVariable Long id) {
        log.debug("REST request to get Datastore : {}", id);
        Optional<Datastore> datastore = datastoreRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(datastore);
    }

    /**
     * DELETE  /datastores/:id : delete the "id" datastore.
     *
     * @param id the id of the datastore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/datastores/{id}")
    @Timed
    public ResponseEntity<Void> deleteDatastore(@PathVariable Long id) {
        log.debug("REST request to delete Datastore : {}", id);

        datastoreRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
