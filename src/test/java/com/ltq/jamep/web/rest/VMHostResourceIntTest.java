package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.VMHost;
import com.ltq.jamep.repository.VMHostRepository;
import com.ltq.jamep.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.ltq.jamep.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VMHostResource REST controller.
 *
 * @see VMHostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class VMHostResourceIntTest {

    private static final String DEFAULT_MO_REF = "AAAAAAAAAA";
    private static final String UPDATED_MO_REF = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private VMHostRepository vMHostRepository;
    @Mock
    private VMHostRepository vMHostRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVMHostMockMvc;

    private VMHost vMHost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VMHostResource vMHostResource = new VMHostResource(vMHostRepository);
        this.restVMHostMockMvc = MockMvcBuilders.standaloneSetup(vMHostResource)
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
    public static VMHost createEntity(EntityManager em) {
        VMHost vMHost = new VMHost()
            .moRef(DEFAULT_MO_REF)
            .name(DEFAULT_NAME)
            .ipAddress(DEFAULT_IP_ADDRESS);
        return vMHost;
    }

    @Before
    public void initTest() {
        vMHost = createEntity(em);
    }

    @Test
    @Transactional
    public void createVMHost() throws Exception {
        int databaseSizeBeforeCreate = vMHostRepository.findAll().size();

        // Create the VMHost
        restVMHostMockMvc.perform(post("/api/vm-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vMHost)))
            .andExpect(status().isCreated());

        // Validate the VMHost in the database
        List<VMHost> vMHostList = vMHostRepository.findAll();
        assertThat(vMHostList).hasSize(databaseSizeBeforeCreate + 1);
        VMHost testVMHost = vMHostList.get(vMHostList.size() - 1);
        assertThat(testVMHost.getMoRef()).isEqualTo(DEFAULT_MO_REF);
        assertThat(testVMHost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVMHost.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createVMHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vMHostRepository.findAll().size();

        // Create the VMHost with an existing ID
        vMHost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVMHostMockMvc.perform(post("/api/vm-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vMHost)))
            .andExpect(status().isBadRequest());

        // Validate the VMHost in the database
        List<VMHost> vMHostList = vMHostRepository.findAll();
        assertThat(vMHostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVMHosts() throws Exception {
        // Initialize the database
        vMHostRepository.saveAndFlush(vMHost);

        // Get all the vMHostList
        restVMHostMockMvc.perform(get("/api/vm-hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vMHost.getId().intValue())))
            .andExpect(jsonPath("$.[*].moRef").value(hasItem(DEFAULT_MO_REF.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())));
    }
    
    public void getAllVMHostsWithEagerRelationshipsIsEnabled() throws Exception {
        VMHostResource vMHostResource = new VMHostResource(vMHostRepositoryMock);
        when(vMHostRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVMHostMockMvc = MockMvcBuilders.standaloneSetup(vMHostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVMHostMockMvc.perform(get("/api/vm-hosts?eagerload=true"))
        .andExpect(status().isOk());

        verify(vMHostRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllVMHostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        VMHostResource vMHostResource = new VMHostResource(vMHostRepositoryMock);
            when(vMHostRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVMHostMockMvc = MockMvcBuilders.standaloneSetup(vMHostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVMHostMockMvc.perform(get("/api/vm-hosts?eagerload=true"))
        .andExpect(status().isOk());

            verify(vMHostRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVMHost() throws Exception {
        // Initialize the database
        vMHostRepository.saveAndFlush(vMHost);

        // Get the vMHost
        restVMHostMockMvc.perform(get("/api/vm-hosts/{id}", vMHost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vMHost.getId().intValue()))
            .andExpect(jsonPath("$.moRef").value(DEFAULT_MO_REF.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVMHost() throws Exception {
        // Get the vMHost
        restVMHostMockMvc.perform(get("/api/vm-hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVMHost() throws Exception {
        // Initialize the database
        vMHostRepository.saveAndFlush(vMHost);

        int databaseSizeBeforeUpdate = vMHostRepository.findAll().size();

        // Update the vMHost
        VMHost updatedVMHost = vMHostRepository.findById(vMHost.getId()).get();
        // Disconnect from session so that the updates on updatedVMHost are not directly saved in db
        em.detach(updatedVMHost);
        updatedVMHost
            .moRef(UPDATED_MO_REF)
            .name(UPDATED_NAME)
            .ipAddress(UPDATED_IP_ADDRESS);

        restVMHostMockMvc.perform(put("/api/vm-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVMHost)))
            .andExpect(status().isOk());

        // Validate the VMHost in the database
        List<VMHost> vMHostList = vMHostRepository.findAll();
        assertThat(vMHostList).hasSize(databaseSizeBeforeUpdate);
        VMHost testVMHost = vMHostList.get(vMHostList.size() - 1);
        assertThat(testVMHost.getMoRef()).isEqualTo(UPDATED_MO_REF);
        assertThat(testVMHost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVMHost.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingVMHost() throws Exception {
        int databaseSizeBeforeUpdate = vMHostRepository.findAll().size();

        // Create the VMHost

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVMHostMockMvc.perform(put("/api/vm-hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vMHost)))
            .andExpect(status().isBadRequest());

        // Validate the VMHost in the database
        List<VMHost> vMHostList = vMHostRepository.findAll();
        assertThat(vMHostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVMHost() throws Exception {
        // Initialize the database
        vMHostRepository.saveAndFlush(vMHost);

        int databaseSizeBeforeDelete = vMHostRepository.findAll().size();

        // Get the vMHost
        restVMHostMockMvc.perform(delete("/api/vm-hosts/{id}", vMHost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VMHost> vMHostList = vMHostRepository.findAll();
        assertThat(vMHostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VMHost.class);
        VMHost vMHost1 = new VMHost();
        vMHost1.setId(1L);
        VMHost vMHost2 = new VMHost();
        vMHost2.setId(vMHost1.getId());
        assertThat(vMHost1).isEqualTo(vMHost2);
        vMHost2.setId(2L);
        assertThat(vMHost1).isNotEqualTo(vMHost2);
        vMHost1.setId(null);
        assertThat(vMHost1).isNotEqualTo(vMHost2);
    }
}
