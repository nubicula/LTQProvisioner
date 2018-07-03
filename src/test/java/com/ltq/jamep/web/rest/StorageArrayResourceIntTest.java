package com.ltq.jamep.web.rest;

import com.ltq.jamep.LtqProvisionerApp;

import com.ltq.jamep.domain.StorageArray;
import com.ltq.jamep.repository.StorageArrayRepository;
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
 * Test class for the StorageArrayResource REST controller.
 *
 * @see StorageArrayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LtqProvisionerApp.class)
public class StorageArrayResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private StorageArrayRepository storageArrayRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStorageArrayMockMvc;

    private StorageArray storageArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StorageArrayResource storageArrayResource = new StorageArrayResource(storageArrayRepository);
        this.restStorageArrayMockMvc = MockMvcBuilders.standaloneSetup(storageArrayResource)
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
    public static StorageArray createEntity(EntityManager em) {
        StorageArray storageArray = new StorageArray()
            .name(DEFAULT_NAME)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD);
        return storageArray;
    }

    @Before
    public void initTest() {
        storageArray = createEntity(em);
    }

    @Test
    @Transactional
    public void createStorageArray() throws Exception {
        int databaseSizeBeforeCreate = storageArrayRepository.findAll().size();

        // Create the StorageArray
        restStorageArrayMockMvc.perform(post("/api/storage-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageArray)))
            .andExpect(status().isCreated());

        // Validate the StorageArray in the database
        List<StorageArray> storageArrayList = storageArrayRepository.findAll();
        assertThat(storageArrayList).hasSize(databaseSizeBeforeCreate + 1);
        StorageArray testStorageArray = storageArrayList.get(storageArrayList.size() - 1);
        assertThat(testStorageArray.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStorageArray.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testStorageArray.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testStorageArray.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createStorageArrayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storageArrayRepository.findAll().size();

        // Create the StorageArray with an existing ID
        storageArray.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorageArrayMockMvc.perform(post("/api/storage-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageArray)))
            .andExpect(status().isBadRequest());

        // Validate the StorageArray in the database
        List<StorageArray> storageArrayList = storageArrayRepository.findAll();
        assertThat(storageArrayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageArrayRepository.findAll().size();
        // set the field null
        storageArray.setIpAddress(null);

        // Create the StorageArray, which fails.

        restStorageArrayMockMvc.perform(post("/api/storage-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageArray)))
            .andExpect(status().isBadRequest());

        List<StorageArray> storageArrayList = storageArrayRepository.findAll();
        assertThat(storageArrayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStorageArrays() throws Exception {
        // Initialize the database
        storageArrayRepository.saveAndFlush(storageArray);

        // Get all the storageArrayList
        restStorageArrayMockMvc.perform(get("/api/storage-arrays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageArray.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    

    @Test
    @Transactional
    public void getStorageArray() throws Exception {
        // Initialize the database
        storageArrayRepository.saveAndFlush(storageArray);

        // Get the storageArray
        restStorageArrayMockMvc.perform(get("/api/storage-arrays/{id}", storageArray.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storageArray.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStorageArray() throws Exception {
        // Get the storageArray
        restStorageArrayMockMvc.perform(get("/api/storage-arrays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStorageArray() throws Exception {
        // Initialize the database
        storageArrayRepository.saveAndFlush(storageArray);

        int databaseSizeBeforeUpdate = storageArrayRepository.findAll().size();

        // Update the storageArray
        StorageArray updatedStorageArray = storageArrayRepository.findById(storageArray.getId()).get();
        // Disconnect from session so that the updates on updatedStorageArray are not directly saved in db
        em.detach(updatedStorageArray);
        updatedStorageArray
            .name(UPDATED_NAME)
            .ipAddress(UPDATED_IP_ADDRESS)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD);

        restStorageArrayMockMvc.perform(put("/api/storage-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStorageArray)))
            .andExpect(status().isOk());

        // Validate the StorageArray in the database
        List<StorageArray> storageArrayList = storageArrayRepository.findAll();
        assertThat(storageArrayList).hasSize(databaseSizeBeforeUpdate);
        StorageArray testStorageArray = storageArrayList.get(storageArrayList.size() - 1);
        assertThat(testStorageArray.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStorageArray.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testStorageArray.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testStorageArray.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingStorageArray() throws Exception {
        int databaseSizeBeforeUpdate = storageArrayRepository.findAll().size();

        // Create the StorageArray

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStorageArrayMockMvc.perform(put("/api/storage-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storageArray)))
            .andExpect(status().isBadRequest());

        // Validate the StorageArray in the database
        List<StorageArray> storageArrayList = storageArrayRepository.findAll();
        assertThat(storageArrayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStorageArray() throws Exception {
        // Initialize the database
        storageArrayRepository.saveAndFlush(storageArray);

        int databaseSizeBeforeDelete = storageArrayRepository.findAll().size();

        // Get the storageArray
        restStorageArrayMockMvc.perform(delete("/api/storage-arrays/{id}", storageArray.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StorageArray> storageArrayList = storageArrayRepository.findAll();
        assertThat(storageArrayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StorageArray.class);
        StorageArray storageArray1 = new StorageArray();
        storageArray1.setId(1L);
        StorageArray storageArray2 = new StorageArray();
        storageArray2.setId(storageArray1.getId());
        assertThat(storageArray1).isEqualTo(storageArray2);
        storageArray2.setId(2L);
        assertThat(storageArray1).isNotEqualTo(storageArray2);
        storageArray1.setId(null);
        assertThat(storageArray1).isNotEqualTo(storageArray2);
    }
}
