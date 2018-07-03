package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.DatastoreCluster;
import com.ltq.jamep.repository.DatastoreClusterRepository;
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
 * Test class for the DatastoreClusterResource REST controller.
 *
 * @see DatastoreClusterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class DatastoreClusterResourceIntTest {

    private static final String DEFAULT_MO_REF = "AAAAAAAAAA";
    private static final String UPDATED_MO_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DatastoreClusterRepository datastoreClusterRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDatastoreClusterMockMvc;

    private DatastoreCluster datastoreCluster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatastoreClusterResource datastoreClusterResource = new DatastoreClusterResource(datastoreClusterRepository);
        this.restDatastoreClusterMockMvc = MockMvcBuilders.standaloneSetup(datastoreClusterResource)
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
    public static DatastoreCluster createEntity(EntityManager em) {
        DatastoreCluster datastoreCluster = new DatastoreCluster()
            .moRef(DEFAULT_MO_REF)
            .name(DEFAULT_NAME);
        return datastoreCluster;
    }

    @Before
    public void initTest() {
        datastoreCluster = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatastoreCluster() throws Exception {
        int databaseSizeBeforeCreate = datastoreClusterRepository.findAll().size();

        // Create the DatastoreCluster
        restDatastoreClusterMockMvc.perform(post("/api/datastore-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datastoreCluster)))
            .andExpect(status().isCreated());

        // Validate the DatastoreCluster in the database
        List<DatastoreCluster> datastoreClusterList = datastoreClusterRepository.findAll();
        assertThat(datastoreClusterList).hasSize(databaseSizeBeforeCreate + 1);
        DatastoreCluster testDatastoreCluster = datastoreClusterList.get(datastoreClusterList.size() - 1);
        assertThat(testDatastoreCluster.getMoRef()).isEqualTo(DEFAULT_MO_REF);
        assertThat(testDatastoreCluster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDatastoreClusterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datastoreClusterRepository.findAll().size();

        // Create the DatastoreCluster with an existing ID
        datastoreCluster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatastoreClusterMockMvc.perform(post("/api/datastore-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datastoreCluster)))
            .andExpect(status().isBadRequest());

        // Validate the DatastoreCluster in the database
        List<DatastoreCluster> datastoreClusterList = datastoreClusterRepository.findAll();
        assertThat(datastoreClusterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDatastoreClusters() throws Exception {
        // Initialize the database
        datastoreClusterRepository.saveAndFlush(datastoreCluster);

        // Get all the datastoreClusterList
        restDatastoreClusterMockMvc.perform(get("/api/datastore-clusters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datastoreCluster.getId().intValue())))
            .andExpect(jsonPath("$.[*].moRef").value(hasItem(DEFAULT_MO_REF.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getDatastoreCluster() throws Exception {
        // Initialize the database
        datastoreClusterRepository.saveAndFlush(datastoreCluster);

        // Get the datastoreCluster
        restDatastoreClusterMockMvc.perform(get("/api/datastore-clusters/{id}", datastoreCluster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datastoreCluster.getId().intValue()))
            .andExpect(jsonPath("$.moRef").value(DEFAULT_MO_REF.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDatastoreCluster() throws Exception {
        // Get the datastoreCluster
        restDatastoreClusterMockMvc.perform(get("/api/datastore-clusters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatastoreCluster() throws Exception {
        // Initialize the database
        datastoreClusterRepository.saveAndFlush(datastoreCluster);

        int databaseSizeBeforeUpdate = datastoreClusterRepository.findAll().size();

        // Update the datastoreCluster
        DatastoreCluster updatedDatastoreCluster = datastoreClusterRepository.findById(datastoreCluster.getId()).get();
        // Disconnect from session so that the updates on updatedDatastoreCluster are not directly saved in db
        em.detach(updatedDatastoreCluster);
        updatedDatastoreCluster
            .moRef(UPDATED_MO_REF)
            .name(UPDATED_NAME);

        restDatastoreClusterMockMvc.perform(put("/api/datastore-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDatastoreCluster)))
            .andExpect(status().isOk());

        // Validate the DatastoreCluster in the database
        List<DatastoreCluster> datastoreClusterList = datastoreClusterRepository.findAll();
        assertThat(datastoreClusterList).hasSize(databaseSizeBeforeUpdate);
        DatastoreCluster testDatastoreCluster = datastoreClusterList.get(datastoreClusterList.size() - 1);
        assertThat(testDatastoreCluster.getMoRef()).isEqualTo(UPDATED_MO_REF);
        assertThat(testDatastoreCluster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDatastoreCluster() throws Exception {
        int databaseSizeBeforeUpdate = datastoreClusterRepository.findAll().size();

        // Create the DatastoreCluster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDatastoreClusterMockMvc.perform(put("/api/datastore-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datastoreCluster)))
            .andExpect(status().isBadRequest());

        // Validate the DatastoreCluster in the database
        List<DatastoreCluster> datastoreClusterList = datastoreClusterRepository.findAll();
        assertThat(datastoreClusterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatastoreCluster() throws Exception {
        // Initialize the database
        datastoreClusterRepository.saveAndFlush(datastoreCluster);

        int databaseSizeBeforeDelete = datastoreClusterRepository.findAll().size();

        // Get the datastoreCluster
        restDatastoreClusterMockMvc.perform(delete("/api/datastore-clusters/{id}", datastoreCluster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DatastoreCluster> datastoreClusterList = datastoreClusterRepository.findAll();
        assertThat(datastoreClusterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatastoreCluster.class);
        DatastoreCluster datastoreCluster1 = new DatastoreCluster();
        datastoreCluster1.setId(1L);
        DatastoreCluster datastoreCluster2 = new DatastoreCluster();
        datastoreCluster2.setId(datastoreCluster1.getId());
        assertThat(datastoreCluster1).isEqualTo(datastoreCluster2);
        datastoreCluster2.setId(2L);
        assertThat(datastoreCluster1).isNotEqualTo(datastoreCluster2);
        datastoreCluster1.setId(null);
        assertThat(datastoreCluster1).isNotEqualTo(datastoreCluster2);
    }
}
