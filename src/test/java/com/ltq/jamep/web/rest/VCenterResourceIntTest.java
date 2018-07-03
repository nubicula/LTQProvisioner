package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.VCenter;
import com.ltq.jamep.repository.VCenterRepository;
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
 * Test class for the VCenterResource REST controller.
 *
 * @see VCenterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class VCenterResourceIntTest {

    private static final String DEFAULT_MO_REF = "AAAAAAAAAA";
    private static final String UPDATED_MO_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private VCenterRepository vCenterRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVCenterMockMvc;

    private VCenter vCenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VCenterResource vCenterResource = new VCenterResource(vCenterRepository);
        this.restVCenterMockMvc = MockMvcBuilders.standaloneSetup(vCenterResource)
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
    public static VCenter createEntity(EntityManager em) {
        VCenter vCenter = new VCenter()
            .moRef(DEFAULT_MO_REF)
            .name(DEFAULT_NAME)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD);
        return vCenter;
    }

    @Before
    public void initTest() {
        vCenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createVCenter() throws Exception {
        int databaseSizeBeforeCreate = vCenterRepository.findAll().size();

        // Create the VCenter
        restVCenterMockMvc.perform(post("/api/v-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vCenter)))
            .andExpect(status().isCreated());

        // Validate the VCenter in the database
        List<VCenter> vCenterList = vCenterRepository.findAll();
        assertThat(vCenterList).hasSize(databaseSizeBeforeCreate + 1);
        VCenter testVCenter = vCenterList.get(vCenterList.size() - 1);
        assertThat(testVCenter.getMoRef()).isEqualTo(DEFAULT_MO_REF);
        assertThat(testVCenter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVCenter.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testVCenter.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testVCenter.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createVCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vCenterRepository.findAll().size();

        // Create the VCenter with an existing ID
        vCenter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVCenterMockMvc.perform(post("/api/v-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vCenter)))
            .andExpect(status().isBadRequest());

        // Validate the VCenter in the database
        List<VCenter> vCenterList = vCenterRepository.findAll();
        assertThat(vCenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = vCenterRepository.findAll().size();
        // set the field null
        vCenter.setIpAddress(null);

        // Create the VCenter, which fails.

        restVCenterMockMvc.perform(post("/api/v-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vCenter)))
            .andExpect(status().isBadRequest());

        List<VCenter> vCenterList = vCenterRepository.findAll();
        assertThat(vCenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVCenters() throws Exception {
        // Initialize the database
        vCenterRepository.saveAndFlush(vCenter);

        // Get all the vCenterList
        restVCenterMockMvc.perform(get("/api/v-centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].moRef").value(hasItem(DEFAULT_MO_REF.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    

    @Test
    @Transactional
    public void getVCenter() throws Exception {
        // Initialize the database
        vCenterRepository.saveAndFlush(vCenter);

        // Get the vCenter
        restVCenterMockMvc.perform(get("/api/v-centers/{id}", vCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vCenter.getId().intValue()))
            .andExpect(jsonPath("$.moRef").value(DEFAULT_MO_REF.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVCenter() throws Exception {
        // Get the vCenter
        restVCenterMockMvc.perform(get("/api/v-centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVCenter() throws Exception {
        // Initialize the database
        vCenterRepository.saveAndFlush(vCenter);

        int databaseSizeBeforeUpdate = vCenterRepository.findAll().size();

        // Update the vCenter
        VCenter updatedVCenter = vCenterRepository.findById(vCenter.getId()).get();
        // Disconnect from session so that the updates on updatedVCenter are not directly saved in db
        em.detach(updatedVCenter);
        updatedVCenter
            .moRef(UPDATED_MO_REF)
            .name(UPDATED_NAME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD);

        restVCenterMockMvc.perform(put("/api/v-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVCenter)))
            .andExpect(status().isOk());

        // Validate the VCenter in the database
        List<VCenter> vCenterList = vCenterRepository.findAll();
        assertThat(vCenterList).hasSize(databaseSizeBeforeUpdate);
        VCenter testVCenter = vCenterList.get(vCenterList.size() - 1);
        assertThat(testVCenter.getMoRef()).isEqualTo(UPDATED_MO_REF);
        assertThat(testVCenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVCenter.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testVCenter.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testVCenter.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingVCenter() throws Exception {
        int databaseSizeBeforeUpdate = vCenterRepository.findAll().size();

        // Create the VCenter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVCenterMockMvc.perform(put("/api/v-centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vCenter)))
            .andExpect(status().isBadRequest());

        // Validate the VCenter in the database
        List<VCenter> vCenterList = vCenterRepository.findAll();
        assertThat(vCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVCenter() throws Exception {
        // Initialize the database
        vCenterRepository.saveAndFlush(vCenter);

        int databaseSizeBeforeDelete = vCenterRepository.findAll().size();

        // Get the vCenter
        restVCenterMockMvc.perform(delete("/api/v-centers/{id}", vCenter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VCenter> vCenterList = vCenterRepository.findAll();
        assertThat(vCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VCenter.class);
        VCenter vCenter1 = new VCenter();
        vCenter1.setId(1L);
        VCenter vCenter2 = new VCenter();
        vCenter2.setId(vCenter1.getId());
        assertThat(vCenter1).isEqualTo(vCenter2);
        vCenter2.setId(2L);
        assertThat(vCenter1).isNotEqualTo(vCenter2);
        vCenter1.setId(null);
        assertThat(vCenter1).isNotEqualTo(vCenter2);
    }
}
