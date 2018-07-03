package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.PhysicalDatacenter;
import com.ltq.jamep.repository.PhysicalDatacenterRepository;
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
 * Test class for the PhysicalDatacenterResource REST controller.
 *
 * @see PhysicalDatacenterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class PhysicalDatacenterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private PhysicalDatacenterRepository physicalDatacenterRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPhysicalDatacenterMockMvc;

    private PhysicalDatacenter physicalDatacenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhysicalDatacenterResource physicalDatacenterResource = new PhysicalDatacenterResource(physicalDatacenterRepository);
        this.restPhysicalDatacenterMockMvc = MockMvcBuilders.standaloneSetup(physicalDatacenterResource)
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
    public static PhysicalDatacenter createEntity(EntityManager em) {
        PhysicalDatacenter physicalDatacenter = new PhysicalDatacenter()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return physicalDatacenter;
    }

    @Before
    public void initTest() {
        physicalDatacenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhysicalDatacenter() throws Exception {
        int databaseSizeBeforeCreate = physicalDatacenterRepository.findAll().size();

        // Create the PhysicalDatacenter
        restPhysicalDatacenterMockMvc.perform(post("/api/physical-datacenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(physicalDatacenter)))
            .andExpect(status().isCreated());

        // Validate the PhysicalDatacenter in the database
        List<PhysicalDatacenter> physicalDatacenterList = physicalDatacenterRepository.findAll();
        assertThat(physicalDatacenterList).hasSize(databaseSizeBeforeCreate + 1);
        PhysicalDatacenter testPhysicalDatacenter = physicalDatacenterList.get(physicalDatacenterList.size() - 1);
        assertThat(testPhysicalDatacenter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPhysicalDatacenter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createPhysicalDatacenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = physicalDatacenterRepository.findAll().size();

        // Create the PhysicalDatacenter with an existing ID
        physicalDatacenter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhysicalDatacenterMockMvc.perform(post("/api/physical-datacenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(physicalDatacenter)))
            .andExpect(status().isBadRequest());

        // Validate the PhysicalDatacenter in the database
        List<PhysicalDatacenter> physicalDatacenterList = physicalDatacenterRepository.findAll();
        assertThat(physicalDatacenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPhysicalDatacenters() throws Exception {
        // Initialize the database
        physicalDatacenterRepository.saveAndFlush(physicalDatacenter);

        // Get all the physicalDatacenterList
        restPhysicalDatacenterMockMvc.perform(get("/api/physical-datacenters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(physicalDatacenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }
    

    @Test
    @Transactional
    public void getPhysicalDatacenter() throws Exception {
        // Initialize the database
        physicalDatacenterRepository.saveAndFlush(physicalDatacenter);

        // Get the physicalDatacenter
        restPhysicalDatacenterMockMvc.perform(get("/api/physical-datacenters/{id}", physicalDatacenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(physicalDatacenter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPhysicalDatacenter() throws Exception {
        // Get the physicalDatacenter
        restPhysicalDatacenterMockMvc.perform(get("/api/physical-datacenters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhysicalDatacenter() throws Exception {
        // Initialize the database
        physicalDatacenterRepository.saveAndFlush(physicalDatacenter);

        int databaseSizeBeforeUpdate = physicalDatacenterRepository.findAll().size();

        // Update the physicalDatacenter
        PhysicalDatacenter updatedPhysicalDatacenter = physicalDatacenterRepository.findById(physicalDatacenter.getId()).get();
        // Disconnect from session so that the updates on updatedPhysicalDatacenter are not directly saved in db
        em.detach(updatedPhysicalDatacenter);
        updatedPhysicalDatacenter
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);

        restPhysicalDatacenterMockMvc.perform(put("/api/physical-datacenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhysicalDatacenter)))
            .andExpect(status().isOk());

        // Validate the PhysicalDatacenter in the database
        List<PhysicalDatacenter> physicalDatacenterList = physicalDatacenterRepository.findAll();
        assertThat(physicalDatacenterList).hasSize(databaseSizeBeforeUpdate);
        PhysicalDatacenter testPhysicalDatacenter = physicalDatacenterList.get(physicalDatacenterList.size() - 1);
        assertThat(testPhysicalDatacenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPhysicalDatacenter.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingPhysicalDatacenter() throws Exception {
        int databaseSizeBeforeUpdate = physicalDatacenterRepository.findAll().size();

        // Create the PhysicalDatacenter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPhysicalDatacenterMockMvc.perform(put("/api/physical-datacenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(physicalDatacenter)))
            .andExpect(status().isBadRequest());

        // Validate the PhysicalDatacenter in the database
        List<PhysicalDatacenter> physicalDatacenterList = physicalDatacenterRepository.findAll();
        assertThat(physicalDatacenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhysicalDatacenter() throws Exception {
        // Initialize the database
        physicalDatacenterRepository.saveAndFlush(physicalDatacenter);

        int databaseSizeBeforeDelete = physicalDatacenterRepository.findAll().size();

        // Get the physicalDatacenter
        restPhysicalDatacenterMockMvc.perform(delete("/api/physical-datacenters/{id}", physicalDatacenter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PhysicalDatacenter> physicalDatacenterList = physicalDatacenterRepository.findAll();
        assertThat(physicalDatacenterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhysicalDatacenter.class);
        PhysicalDatacenter physicalDatacenter1 = new PhysicalDatacenter();
        physicalDatacenter1.setId(1L);
        PhysicalDatacenter physicalDatacenter2 = new PhysicalDatacenter();
        physicalDatacenter2.setId(physicalDatacenter1.getId());
        assertThat(physicalDatacenter1).isEqualTo(physicalDatacenter2);
        physicalDatacenter2.setId(2L);
        assertThat(physicalDatacenter1).isNotEqualTo(physicalDatacenter2);
        physicalDatacenter1.setId(null);
        assertThat(physicalDatacenter1).isNotEqualTo(physicalDatacenter2);
    }
}
