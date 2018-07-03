package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.Datastore;
import com.ltq.jamep.repository.DatastoreRepository;
import com.ltq.jamep.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.ltq.jamep.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DatastoreResource REST controller.
 *
 * @see DatastoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class DatastoreResourceIntTest {

    private static final String DEFAULT_MO_REF = "AAAAAAAAAA";
    private static final String UPDATED_MO_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WWN = "AAAAAAAAAA";
    private static final String UPDATED_WWN = "BBBBBBBBBB";

    @Autowired
    private DatastoreRepository datastoreRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDatastoreMockMvc;

    private Datastore datastore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatastoreResource datastoreResource = new DatastoreResource(datastoreRepository);
        this.restDatastoreMockMvc = MockMvcBuilders.standaloneSetup(datastoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Datastore createEntity(EntityManager em) {
        Datastore datastore = new Datastore()
            .moRef(DEFAULT_MO_REF)
            .name(DEFAULT_NAME)
            .wwn(DEFAULT_WWN);
        return datastore;
    }

    @Before
    public void initTest() {
        datastore = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatastore() throws Exception {
        int databaseSizeBeforeCreate = datastoreRepository.findAll().size();

        // Create the Datastore
        restDatastoreMockMvc.perform(post("/api/datastores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datastore)))
            .andExpect(status().isCreated());

        // Validate the Datastore in the database
        List<Datastore> datastoreList = datastoreRepository.findAll();
        assertThat(datastoreList).hasSize(databaseSizeBeforeCreate + 1);
        Datastore testDatastore = datastoreList.get(datastoreList.size() - 1);
        assertThat(testDatastore.getMoRef()).isEqualTo(DEFAULT_MO_REF);
        assertThat(testDatastore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDatastore.getWwn()).isEqualTo(DEFAULT_WWN);
    }

    @Test
    @Transactional
    public void createDatastoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datastoreRepository.findAll().size();

        // Create the Datastore with an existing ID
        datastore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatastoreMockMvc.perform(post("/api/datastores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datastore)))
            .andExpect(status().isBadRequest());

        // Validate the Datastore in the database
        List<Datastore> datastoreList = datastoreRepository.findAll();
        assertThat(datastoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDatastores() throws Exception {
        // Initialize the database
        datastoreRepository.saveAndFlush(datastore);

        // Get all the datastoreList
        restDatastoreMockMvc.perform(get("/api/datastores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datastore.getId().intValue())))
            .andExpect(jsonPath("$.[*].moRef").value(hasItem(DEFAULT_MO_REF.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].wwn").value(hasItem(DEFAULT_WWN.toString())));
    }
    

    @Test
    @Transactional
    public void getDatastore() throws Exception {
        // Initialize the database
        datastoreRepository.saveAndFlush(datastore);

        // Get the datastore
        restDatastoreMockMvc.perform(get("/api/datastores/{id}", datastore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datastore.getId().intValue()))
            .andExpect(jsonPath("$.moRef").value(DEFAULT_MO_REF.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.wwn").value(DEFAULT_WWN.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDatastore() throws Exception {
        // Get the datastore
        restDatastoreMockMvc.perform(get("/api/datastores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatastore() throws Exception {
        // Initialize the database
        datastoreRepository.saveAndFlush(datastore);

        int databaseSizeBeforeUpdate = datastoreRepository.findAll().size();

        // Update the datastore
        Datastore updatedDatastore = datastoreRepository.findById(datastore.getId()).get();
        // Disconnect from session so that the updates on updatedDatastore are not directly saved in db
        em.detach(updatedDatastore);
        updatedDatastore
            .moRef(UPDATED_MO_REF)
            .name(UPDATED_NAME)
            .wwn(UPDATED_WWN);

        restDatastoreMockMvc.perform(put("/api/datastores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDatastore)))
            .andExpect(status().isOk());

        // Validate the Datastore in the database
        List<Datastore> datastoreList = datastoreRepository.findAll();
        assertThat(datastoreList).hasSize(databaseSizeBeforeUpdate);
        Datastore testDatastore = datastoreList.get(datastoreList.size() - 1);
        assertThat(testDatastore.getMoRef()).isEqualTo(UPDATED_MO_REF);
        assertThat(testDatastore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDatastore.getWwn()).isEqualTo(UPDATED_WWN);
    }

    @Test
    @Transactional
    public void updateNonExistingDatastore() throws Exception {
        int databaseSizeBeforeUpdate = datastoreRepository.findAll().size();

        // Create the Datastore

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDatastoreMockMvc.perform(put("/api/datastores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datastore)))
            .andExpect(status().isBadRequest());

        // Validate the Datastore in the database
        List<Datastore> datastoreList = datastoreRepository.findAll();
        assertThat(datastoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatastore() throws Exception {
        // Initialize the database
        datastoreRepository.saveAndFlush(datastore);

        int databaseSizeBeforeDelete = datastoreRepository.findAll().size();

        // Get the datastore
        restDatastoreMockMvc.perform(delete("/api/datastores/{id}", datastore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Datastore> datastoreList = datastoreRepository.findAll();
        assertThat(datastoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Datastore.class);
        Datastore datastore1 = new Datastore();
        datastore1.setId(1L);
        Datastore datastore2 = new Datastore();
        datastore2.setId(datastore1.getId());
        assertThat(datastore1).isEqualTo(datastore2);
        datastore2.setId(2L);
        assertThat(datastore1).isNotEqualTo(datastore2);
        datastore1.setId(null);
        assertThat(datastore1).isNotEqualTo(datastore2);
    }
}
