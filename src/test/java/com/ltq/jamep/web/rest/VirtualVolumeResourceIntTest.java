package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.VirtualVolume;
import com.ltq.jamep.repository.VirtualVolumeRepository;
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
 * Test class for the VirtualVolumeResource REST controller.
 *
 * @see VirtualVolumeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class VirtualVolumeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WWN = "AAAAAAAAAA";
    private static final String UPDATED_WWN = "BBBBBBBBBB";

    private static final String DEFAULT_LUN_ID = "AAAAAAAAAA";
    private static final String UPDATED_LUN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PEER_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_PEER_VOLUME = "BBBBBBBBBB";

    @Autowired
    private VirtualVolumeRepository virtualVolumeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVirtualVolumeMockMvc;

    private VirtualVolume virtualVolume;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VirtualVolumeResource virtualVolumeResource = new VirtualVolumeResource(virtualVolumeRepository);
        this.restVirtualVolumeMockMvc = MockMvcBuilders.standaloneSetup(virtualVolumeResource)
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
    public static VirtualVolume createEntity(EntityManager em) {
        VirtualVolume virtualVolume = new VirtualVolume()
            .name(DEFAULT_NAME)
            .wwn(DEFAULT_WWN)
            .lunID(DEFAULT_LUN_ID)
            .peerVolume(DEFAULT_PEER_VOLUME);
        return virtualVolume;
    }

    @Before
    public void initTest() {
        virtualVolume = createEntity(em);
    }

    @Test
    @Transactional
    public void createVirtualVolume() throws Exception {
        int databaseSizeBeforeCreate = virtualVolumeRepository.findAll().size();

        // Create the VirtualVolume
        restVirtualVolumeMockMvc.perform(post("/api/virtual-volumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualVolume)))
            .andExpect(status().isCreated());

        // Validate the VirtualVolume in the database
        List<VirtualVolume> virtualVolumeList = virtualVolumeRepository.findAll();
        assertThat(virtualVolumeList).hasSize(databaseSizeBeforeCreate + 1);
        VirtualVolume testVirtualVolume = virtualVolumeList.get(virtualVolumeList.size() - 1);
        assertThat(testVirtualVolume.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVirtualVolume.getWwn()).isEqualTo(DEFAULT_WWN);
        assertThat(testVirtualVolume.getLunID()).isEqualTo(DEFAULT_LUN_ID);
        assertThat(testVirtualVolume.getPeerVolume()).isEqualTo(DEFAULT_PEER_VOLUME);
    }

    @Test
    @Transactional
    public void createVirtualVolumeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = virtualVolumeRepository.findAll().size();

        // Create the VirtualVolume with an existing ID
        virtualVolume.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVirtualVolumeMockMvc.perform(post("/api/virtual-volumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualVolume)))
            .andExpect(status().isBadRequest());

        // Validate the VirtualVolume in the database
        List<VirtualVolume> virtualVolumeList = virtualVolumeRepository.findAll();
        assertThat(virtualVolumeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVirtualVolumes() throws Exception {
        // Initialize the database
        virtualVolumeRepository.saveAndFlush(virtualVolume);

        // Get all the virtualVolumeList
        restVirtualVolumeMockMvc.perform(get("/api/virtual-volumes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(virtualVolume.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].wwn").value(hasItem(DEFAULT_WWN.toString())))
            .andExpect(jsonPath("$.[*].lunID").value(hasItem(DEFAULT_LUN_ID.toString())))
            .andExpect(jsonPath("$.[*].peerVolume").value(hasItem(DEFAULT_PEER_VOLUME.toString())));
    }
    

    @Test
    @Transactional
    public void getVirtualVolume() throws Exception {
        // Initialize the database
        virtualVolumeRepository.saveAndFlush(virtualVolume);

        // Get the virtualVolume
        restVirtualVolumeMockMvc.perform(get("/api/virtual-volumes/{id}", virtualVolume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(virtualVolume.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.wwn").value(DEFAULT_WWN.toString()))
            .andExpect(jsonPath("$.lunID").value(DEFAULT_LUN_ID.toString()))
            .andExpect(jsonPath("$.peerVolume").value(DEFAULT_PEER_VOLUME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVirtualVolume() throws Exception {
        // Get the virtualVolume
        restVirtualVolumeMockMvc.perform(get("/api/virtual-volumes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVirtualVolume() throws Exception {
        // Initialize the database
        virtualVolumeRepository.saveAndFlush(virtualVolume);

        int databaseSizeBeforeUpdate = virtualVolumeRepository.findAll().size();

        // Update the virtualVolume
        VirtualVolume updatedVirtualVolume = virtualVolumeRepository.findById(virtualVolume.getId()).get();
        // Disconnect from session so that the updates on updatedVirtualVolume are not directly saved in db
        em.detach(updatedVirtualVolume);
        updatedVirtualVolume
            .name(UPDATED_NAME)
            .wwn(UPDATED_WWN)
            .lunID(UPDATED_LUN_ID)
            .peerVolume(UPDATED_PEER_VOLUME);

        restVirtualVolumeMockMvc.perform(put("/api/virtual-volumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVirtualVolume)))
            .andExpect(status().isOk());

        // Validate the VirtualVolume in the database
        List<VirtualVolume> virtualVolumeList = virtualVolumeRepository.findAll();
        assertThat(virtualVolumeList).hasSize(databaseSizeBeforeUpdate);
        VirtualVolume testVirtualVolume = virtualVolumeList.get(virtualVolumeList.size() - 1);
        assertThat(testVirtualVolume.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVirtualVolume.getWwn()).isEqualTo(UPDATED_WWN);
        assertThat(testVirtualVolume.getLunID()).isEqualTo(UPDATED_LUN_ID);
        assertThat(testVirtualVolume.getPeerVolume()).isEqualTo(UPDATED_PEER_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingVirtualVolume() throws Exception {
        int databaseSizeBeforeUpdate = virtualVolumeRepository.findAll().size();

        // Create the VirtualVolume

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVirtualVolumeMockMvc.perform(put("/api/virtual-volumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualVolume)))
            .andExpect(status().isBadRequest());

        // Validate the VirtualVolume in the database
        List<VirtualVolume> virtualVolumeList = virtualVolumeRepository.findAll();
        assertThat(virtualVolumeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVirtualVolume() throws Exception {
        // Initialize the database
        virtualVolumeRepository.saveAndFlush(virtualVolume);

        int databaseSizeBeforeDelete = virtualVolumeRepository.findAll().size();

        // Get the virtualVolume
        restVirtualVolumeMockMvc.perform(delete("/api/virtual-volumes/{id}", virtualVolume.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VirtualVolume> virtualVolumeList = virtualVolumeRepository.findAll();
        assertThat(virtualVolumeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VirtualVolume.class);
        VirtualVolume virtualVolume1 = new VirtualVolume();
        virtualVolume1.setId(1L);
        VirtualVolume virtualVolume2 = new VirtualVolume();
        virtualVolume2.setId(virtualVolume1.getId());
        assertThat(virtualVolume1).isEqualTo(virtualVolume2);
        virtualVolume2.setId(2L);
        assertThat(virtualVolume1).isNotEqualTo(virtualVolume2);
        virtualVolume1.setId(null);
        assertThat(virtualVolume1).isNotEqualTo(virtualVolume2);
    }
}
