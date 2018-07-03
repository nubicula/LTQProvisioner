package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.DatastoreCluster;
import com.ltq.jamep.repository.DatastoreClusterRepository;
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
 * REST controller for managing DatastoreCluster.
 */
@RestController
@RequestMapping("/api")
public class DatastoreClusterResource {

    private final Logger log = LoggerFactory.getLogger(DatastoreClusterResource.class);

    private static final String ENTITY_NAME = "datastoreCluster";

    private final DatastoreClusterRepository datastoreClusterRepository;

    public DatastoreClusterResource(DatastoreClusterRepository datastoreClusterRepository) {
        this.datastoreClusterRepository = datastoreClusterRepository;
    }

    /**
     * POST  /datastore-clusters : Create a new datastoreCluster.
     *
     * @param datastoreCluster the datastoreCluster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new datastoreCluster, or with status 400 (Bad Request) if the datastoreCluster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/datastore-clusters")
    @Timed
    public ResponseEntity<DatastoreCluster> createDatastoreCluster(@RequestBody DatastoreCluster datastoreCluster) throws URISyntaxException {
        log.debug("REST request to save DatastoreCluster : {}", datastoreCluster);
        if (datastoreCluster.getId() != null) {
            throw new BadRequestAlertException("A new datastoreCluster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatastoreCluster result = datastoreClusterRepository.save(datastoreCluster);
        return ResponseEntity.created(new URI("/api/datastore-clusters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /datastore-clusters : Updates an existing datastoreCluster.
     *
     * @param datastoreCluster the datastoreCluster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated datastoreCluster,
     * or with status 400 (Bad Request) if the datastoreCluster is not valid,
     * or with status 500 (Internal Server Error) if the datastoreCluster couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/datastore-clusters")
    @Timed
    public ResponseEntity<DatastoreCluster> updateDatastoreCluster(@RequestBody DatastoreCluster datastoreCluster) throws URISyntaxException {
        log.debug("REST request to update DatastoreCluster : {}", datastoreCluster);
        if (datastoreCluster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatastoreCluster result = datastoreClusterRepository.save(datastoreCluster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, datastoreCluster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /datastore-clusters : get all the datastoreClusters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of datastoreClusters in body
     */
    @GetMapping("/datastore-clusters")
    @Timed
    public List<DatastoreCluster> getAllDatastoreClusters() {
        log.debug("REST request to get all DatastoreClusters");
        return datastoreClusterRepository.findAll();
    }

    /**
     * GET  /datastore-clusters/:id : get the "id" datastoreCluster.
     *
     * @param id the id of the datastoreCluster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the datastoreCluster, or with status 404 (Not Found)
     */
    @GetMapping("/datastore-clusters/{id}")
    @Timed
    public ResponseEntity<DatastoreCluster> getDatastoreCluster(@PathVariable Long id) {
        log.debug("REST request to get DatastoreCluster : {}", id);
        Optional<DatastoreCluster> datastoreCluster = datastoreClusterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(datastoreCluster);
    }

    /**
     * DELETE  /datastore-clusters/:id : delete the "id" datastoreCluster.
     *
     * @param id the id of the datastoreCluster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/datastore-clusters/{id}")
    @Timed
    public ResponseEntity<Void> deleteDatastoreCluster(@PathVariable Long id) {
        log.debug("REST request to delete DatastoreCluster : {}", id);

        datastoreClusterRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
