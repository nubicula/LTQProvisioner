package com.ltq.jamep.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ltq.jamep.domain.StorageArray;
import com.ltq.jamep.repository.StorageArrayRepository;
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
 * REST controller for managing StorageArray.
 */
@RestController
@RequestMapping("/api")
public class StorageArrayResource {

    private final Logger log = LoggerFactory.getLogger(StorageArrayResource.class);

    private static final String ENTITY_NAME = "storageArray";

    private final StorageArrayRepository storageArrayRepository;

    public StorageArrayResource(StorageArrayRepository storageArrayRepository) {
        this.storageArrayRepository = storageArrayRepository;
    }

    /**
     * POST  /storage-arrays : Create a new storageArray.
     *
     * @param storageArray the storageArray to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storageArray, or with status 400 (Bad Request) if the storageArray has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/storage-arrays")
    @Timed
    public ResponseEntity<StorageArray> createStorageArray(@Valid @RequestBody StorageArray storageArray) throws URISyntaxException {
        log.debug("REST request to save StorageArray : {}", storageArray);
        if (storageArray.getId() != null) {
            throw new BadRequestAlertException("A new storageArray cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorageArray result = storageArrayRepository.save(storageArray);
        return ResponseEntity.created(new URI("/api/storage-arrays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /storage-arrays : Updates an existing storageArray.
     *
     * @param storageArray the storageArray to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storageArray,
     * or with status 400 (Bad Request) if the storageArray is not valid,
     * or with status 500 (Internal Server Error) if the storageArray couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/storage-arrays")
    @Timed
    public ResponseEntity<StorageArray> updateStorageArray(@Valid @RequestBody StorageArray storageArray) throws URISyntaxException {
        log.debug("REST request to update StorageArray : {}", storageArray);
        if (storageArray.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StorageArray result = storageArrayRepository.save(storageArray);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storageArray.getId().toString()))
            .body(result);
    }

    /**
     * GET  /storage-arrays : get all the storageArrays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storageArrays in body
     */
    @GetMapping("/storage-arrays")
    @Timed
    public List<StorageArray> getAllStorageArrays() {
        log.debug("REST request to get all StorageArrays");
        return storageArrayRepository.findAll();
    }

    /**
     * GET  /storage-arrays/:id : get the "id" storageArray.
     *
     * @param id the id of the storageArray to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storageArray, or with status 404 (Not Found)
     */
    @GetMapping("/storage-arrays/{id}")
    @Timed
    public ResponseEntity<StorageArray> getStorageArray(@PathVariable Long id) {
        log.debug("REST request to get StorageArray : {}", id);
        Optional<StorageArray> storageArray = storageArrayRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(storageArray);
    }

    /**
     * DELETE  /storage-arrays/:id : delete the "id" storageArray.
     *
     * @param id the id of the storageArray to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/storage-arrays/{id}")
    @Timed
    public ResponseEntity<Void> deleteStorageArray(@PathVariable Long id) {
        log.debug("REST request to delete StorageArray : {}", id);

        storageArrayRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
