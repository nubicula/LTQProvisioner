package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.VMHostCluster;
import com.ltq.jamep.repository.VMHostClusterRepository;
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
 * Test class for the VMHostClusterResource REST controller.
 *
 * @see VMHostClusterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class VMHostClusterResourceIntTest {

    private static final String DEFAULT_MO_REF = "AAAAAAAAAA";
    private static final String UPDATED_MO_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private VMHostClusterRepository vMHostClusterRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVMHostClusterMockMvc;

    private VMHostCluster vMHostCluster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VMHostClusterResource vMHostClusterResource = new VMHostClusterResource(vMHostClusterRepository);
        this.restVMHostClusterMockMvc = MockMvcBuilders.standaloneSetup(vMHostClusterResource)
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
    public static VMHostCluster createEntity(EntityManager em) {
        VMHostCluster vMHostCluster = new VMHostCluster()
            .moRef(DEFAULT_MO_REF)
            .name(DEFAULT_NAME);
        return vMHostCluster;
    }

    @Before
    public void initTest() {
        vMHostCluster = createEntity(em);
    }

    @Test
    @Transactional
    public void createVMHostCluster() throws Exception {
        int databaseSizeBeforeCreate = vMHostClusterRepository.findAll().size();

        // Create the VMHostCluster
        restVMHostClusterMockMvc.perform(post("/api/vm-host-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vMHostCluster)))
            .andExpect(status().isCreated());

        // Validate the VMHostCluster in the database
        List<VMHostCluster> vMHostClusterList = vMHostClusterRepository.findAll();
        assertThat(vMHostClusterList).hasSize(databaseSizeBeforeCreate + 1);
        VMHostCluster testVMHostCluster = vMHostClusterList.get(vMHostClusterList.size() - 1);
        assertThat(testVMHostCluster.getMoRef()).isEqualTo(DEFAULT_MO_REF);
        assertThat(testVMHostCluster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createVMHostClusterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vMHostClusterRepository.findAll().size();

        // Create the VMHostCluster with an existing ID
        vMHostCluster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVMHostClusterMockMvc.perform(post("/api/vm-host-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vMHostCluster)))
            .andExpect(status().isBadRequest());

        // Validate the VMHostCluster in the database
        List<VMHostCluster> vMHostClusterList = vMHostClusterRepository.findAll();
        assertThat(vMHostClusterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVMHostClusters() throws Exception {
        // Initialize the database
        vMHostClusterRepository.saveAndFlush(vMHostCluster);

        // Get all the vMHostClusterList
        restVMHostClusterMockMvc.perform(get("/api/vm-host-clusters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vMHostCluster.getId().intValue())))
            .andExpect(jsonPath("$.[*].moRef").value(hasItem(DEFAULT_MO_REF.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getVMHostCluster() throws Exception {
        // Initialize the database
        vMHostClusterRepository.saveAndFlush(vMHostCluster);

        // Get the vMHostCluster
        restVMHostClusterMockMvc.perform(get("/api/vm-host-clusters/{id}", vMHostCluster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vMHostCluster.getId().intValue()))
            .andExpect(jsonPath("$.moRef").value(DEFAULT_MO_REF.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVMHostCluster() throws Exception {
        // Get the vMHostCluster
        restVMHostClusterMockMvc.perform(get("/api/vm-host-clusters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVMHostCluster() throws Exception {
        // Initialize the database
        vMHostClusterRepository.saveAndFlush(vMHostCluster);

        int databaseSizeBeforeUpdate = vMHostClusterRepository.findAll().size();

        // Update the vMHostCluster
        VMHostCluster updatedVMHostCluster = vMHostClusterRepository.findById(vMHostCluster.getId()).get();
        // Disconnect from session so that the updates on updatedVMHostCluster are not directly saved in db
        em.detach(updatedVMHostCluster);
        updatedVMHostCluster
            .moRef(UPDATED_MO_REF)
            .name(UPDATED_NAME);

        restVMHostClusterMockMvc.perform(put("/api/vm-host-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVMHostCluster)))
            .andExpect(status().isOk());

        // Validate the VMHostCluster in the database
        List<VMHostCluster> vMHostClusterList = vMHostClusterRepository.findAll();
        assertThat(vMHostClusterList).hasSize(databaseSizeBeforeUpdate);
        VMHostCluster testVMHostCluster = vMHostClusterList.get(vMHostClusterList.size() - 1);
        assertThat(testVMHostCluster.getMoRef()).isEqualTo(UPDATED_MO_REF);
        assertThat(testVMHostCluster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingVMHostCluster() throws Exception {
        int databaseSizeBeforeUpdate = vMHostClusterRepository.findAll().size();

        // Create the VMHostCluster

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVMHostClusterMockMvc.perform(put("/api/vm-host-clusters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vMHostCluster)))
            .andExpect(status().isBadRequest());

        // Validate the VMHostCluster in the database
        List<VMHostCluster> vMHostClusterList = vMHostClusterRepository.findAll();
        assertThat(vMHostClusterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVMHostCluster() throws Exception {
        // Initialize the database
        vMHostClusterRepository.saveAndFlush(vMHostCluster);

        int databaseSizeBeforeDelete = vMHostClusterRepository.findAll().size();

        // Get the vMHostCluster
        restVMHostClusterMockMvc.perform(delete("/api/vm-host-clusters/{id}", vMHostCluster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VMHostCluster> vMHostClusterList = vMHostClusterRepository.findAll();
        assertThat(vMHostClusterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VMHostCluster.class);
        VMHostCluster vMHostCluster1 = new VMHostCluster();
        vMHostCluster1.setId(1L);
        VMHostCluster vMHostCluster2 = new VMHostCluster();
        vMHostCluster2.setId(vMHostCluster1.getId());
        assertThat(vMHostCluster1).isEqualTo(vMHostCluster2);
        vMHostCluster2.setId(2L);
        assertThat(vMHostCluster1).isNotEqualTo(vMHostCluster2);
        vMHostCluster1.setId(null);
        assertThat(vMHostCluster1).isNotEqualTo(vMHostCluster2);
    }
}
