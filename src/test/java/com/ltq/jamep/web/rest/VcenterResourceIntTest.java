package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.Vcenter;
import com.ltq.jamep.repository.VcenterRepository;
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
 * Test class for the VcenterResource REST controller.
 *
 * @see VcenterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class VcenterResourceIntTest {

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
    private VcenterRepository vcenterRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVcenterMockMvc;

    private Vcenter vcenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VcenterResource vcenterResource = new VcenterResource(vcenterRepository);
        this.restVcenterMockMvc = MockMvcBuilders.standaloneSetup(vcenterResource)
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
    public static Vcenter createEntity(EntityManager em) {
        Vcenter vcenter = new Vcenter()
            .moRef(DEFAULT_MO_REF)
            .name(DEFAULT_NAME)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD);
        return vcenter;
    }

    @Before
    public void initTest() {
        vcenter = createEntity(em);
    }

    @Test
    @Transactional
    public void createVcenter() throws Exception {
        int databaseSizeBeforeCreate = vcenterRepository.findAll().size();

        // Create the Vcenter
        restVcenterMockMvc.perform(post("/api/vcenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vcenter)))
            .andExpect(status().isCreated());

        // Validate the Vcenter in the database
        List<Vcenter> vcenterList = vcenterRepository.findAll();
        assertThat(vcenterList).hasSize(databaseSizeBeforeCreate + 1);
        Vcenter testVcenter = vcenterList.get(vcenterList.size() - 1);
        assertThat(testVcenter.getMoRef()).isEqualTo(DEFAULT_MO_REF);
        assertThat(testVcenter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVcenter.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testVcenter.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testVcenter.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createVcenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vcenterRepository.findAll().size();

        // Create the Vcenter with an existing ID
        vcenter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVcenterMockMvc.perform(post("/api/vcenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vcenter)))
            .andExpect(status().isBadRequest());

        // Validate the Vcenter in the database
        List<Vcenter> vcenterList = vcenterRepository.findAll();
        assertThat(vcenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = vcenterRepository.findAll().size();
        // set the field null
        vcenter.setIpAddress(null);

        // Create the Vcenter, which fails.

        restVcenterMockMvc.perform(post("/api/vcenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vcenter)))
            .andExpect(status().isBadRequest());

        List<Vcenter> vcenterList = vcenterRepository.findAll();
        assertThat(vcenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVcenters() throws Exception {
        // Initialize the database
        vcenterRepository.saveAndFlush(vcenter);

        // Get all the vcenterList
        restVcenterMockMvc.perform(get("/api/vcenters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vcenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].moRef").value(hasItem(DEFAULT_MO_REF.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    

    @Test
    @Transactional
    public void getVcenter() throws Exception {
        // Initialize the database
        vcenterRepository.saveAndFlush(vcenter);

        // Get the vcenter
        restVcenterMockMvc.perform(get("/api/vcenters/{id}", vcenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vcenter.getId().intValue()))
            .andExpect(jsonPath("$.moRef").value(DEFAULT_MO_REF.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVcenter() throws Exception {
        // Get the vcenter
        restVcenterMockMvc.perform(get("/api/vcenters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVcenter() throws Exception {
        // Initialize the database
        vcenterRepository.saveAndFlush(vcenter);

        int databaseSizeBeforeUpdate = vcenterRepository.findAll().size();

        // Update the vcenter
        Vcenter updatedVcenter = vcenterRepository.findById(vcenter.getId()).get();
        // Disconnect from session so that the updates on updatedVcenter are not directly saved in db
        em.detach(updatedVcenter);
        updatedVcenter
            .moRef(UPDATED_MO_REF)
            .name(UPDATED_NAME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD);

        restVcenterMockMvc.perform(put("/api/vcenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVcenter)))
            .andExpect(status().isOk());

        // Validate the Vcenter in the database
        List<Vcenter> vcenterList = vcenterRepository.findAll();
        assertThat(vcenterList).hasSize(databaseSizeBeforeUpdate);
        Vcenter testVcenter = vcenterList.get(vcenterList.size() - 1);
        assertThat(testVcenter.getMoRef()).isEqualTo(UPDATED_MO_REF);
        assertThat(testVcenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVcenter.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testVcenter.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testVcenter.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingVcenter() throws Exception {
        int databaseSizeBeforeUpdate = vcenterRepository.findAll().size();

        // Create the Vcenter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVcenterMockMvc.perform(put("/api/vcenters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vcenter)))
            .andExpect(status().isBadRequest());

        // Validate the Vcenter in the database
        List<Vcenter> vcenterList = vcenterRepository.findAll();
        assertThat(vcenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVcenter() throws Exception {
        // Initialize the database
        vcenterRepository.saveAndFlush(vcenter);

        int databaseSizeBeforeDelete = vcenterRepository.findAll().size();

        // Get the vcenter
        restVcenterMockMvc.perform(delete("/api/vcenters/{id}", vcenter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vcenter> vcenterList = vcenterRepository.findAll();
        assertThat(vcenterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vcenter.class);
        Vcenter vcenter1 = new Vcenter();
        vcenter1.setId(1L);
        Vcenter vcenter2 = new Vcenter();
        vcenter2.setId(vcenter1.getId());
        assertThat(vcenter1).isEqualTo(vcenter2);
        vcenter2.setId(2L);
        assertThat(vcenter1).isNotEqualTo(vcenter2);
        vcenter1.setId(null);
        assertThat(vcenter1).isNotEqualTo(vcenter2);
    }
}
