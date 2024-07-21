package com.mycompany.company.web.rest;

import static com.mycompany.company.domain.DepartmentAsserts.*;
import static com.mycompany.company.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.company.IntegrationTest;
import com.mycompany.company.domain.Department;
import com.mycompany.company.domain.enumeration.dept_st;
import com.mycompany.company.repository.DepartmentRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link DepartmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DepartmentResourceIT {

    private static final String DEFAULT_DEPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPT_SNAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_SNAME = "BBBBBBBBBB";

    private static final dept_st DEFAULT_DEPT_STATUS = dept_st.ACTIVE;
    private static final dept_st UPDATED_DEPT_STATUS = dept_st.NOT_ACTIVE;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/departments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Department department;

    private Department insertedDepartment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createEntity() {
        Department department = new Department()
            .dept_name(DEFAULT_DEPT_NAME)
            .dept_sname(DEFAULT_DEPT_SNAME)
            .dept_status(DEFAULT_DEPT_STATUS)
            .date(DEFAULT_DATE);
        return department;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createUpdatedEntity() {
        Department department = new Department()
            .dept_name(UPDATED_DEPT_NAME)
            .dept_sname(UPDATED_DEPT_SNAME)
            .dept_status(UPDATED_DEPT_STATUS)
            .date(UPDATED_DATE);
        return department;
    }

    @BeforeEach
    public void initTest() {
        department = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDepartment != null) {
            departmentRepository.delete(insertedDepartment).block();
            insertedDepartment = null;
        }
    }

    @Test
    void createDepartment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Department
        var returnedDepartment = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Department.class)
            .returnResult()
            .getResponseBody();

        // Validate the Department in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDepartmentUpdatableFieldsEquals(returnedDepartment, getPersistedDepartment(returnedDepartment));

        insertedDepartment = returnedDepartment;
    }

    @Test
    void createDepartmentWithExistingId() throws Exception {
        // Create the Department with an existing ID
        department.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDept_nameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setDept_name(null);

        // Create the Department, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDept_snameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setDept_sname(null);

        // Create the Department, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDept_statusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        department.setDept_status(null);

        // Create the Department, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDepartments() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get all the departmentList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(department.getId()))
            .jsonPath("$.[*].dept_name")
            .value(hasItem(DEFAULT_DEPT_NAME))
            .jsonPath("$.[*].dept_sname")
            .value(hasItem(DEFAULT_DEPT_SNAME))
            .jsonPath("$.[*].dept_status")
            .value(hasItem(DEFAULT_DEPT_STATUS.toString()))
            .jsonPath("$.[*].date")
            .value(hasItem(DEFAULT_DATE.toString()));
    }

    @Test
    void getDepartment() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        // Get the department
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, department.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(department.getId()))
            .jsonPath("$.dept_name")
            .value(is(DEFAULT_DEPT_NAME))
            .jsonPath("$.dept_sname")
            .value(is(DEFAULT_DEPT_SNAME))
            .jsonPath("$.dept_status")
            .value(is(DEFAULT_DEPT_STATUS.toString()))
            .jsonPath("$.date")
            .value(is(DEFAULT_DATE.toString()));
    }

    @Test
    void getNonExistingDepartment() {
        // Get the department
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDepartment() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department
        Department updatedDepartment = departmentRepository.findById(department.getId()).block();
        updatedDepartment.dept_name(UPDATED_DEPT_NAME).dept_sname(UPDATED_DEPT_SNAME).dept_status(UPDATED_DEPT_STATUS).date(UPDATED_DATE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedDepartment.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartmentToMatchAllProperties(updatedDepartment);
    }

    @Test
    void putNonExistingDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, department.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

        partialUpdatedDepartment.dept_name(UPDATED_DEPT_NAME).dept_sname(UPDATED_DEPT_SNAME).date(UPDATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Department in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartment, department),
            getPersistedDepartment(department)
        );
    }

    @Test
    void fullUpdateDepartmentWithPatch() throws Exception {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the department using partial update
        Department partialUpdatedDepartment = new Department();
        partialUpdatedDepartment.setId(department.getId());

        partialUpdatedDepartment
            .dept_name(UPDATED_DEPT_NAME)
            .dept_sname(UPDATED_DEPT_SNAME)
            .dept_status(UPDATED_DEPT_STATUS)
            .date(UPDATED_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDepartment.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDepartment))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Department in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartmentUpdatableFieldsEquals(partialUpdatedDepartment, getPersistedDepartment(partialUpdatedDepartment));
    }

    @Test
    void patchNonExistingDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, department.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDepartment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        department.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(department))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Department in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDepartment() {
        // Initialize the database
        insertedDepartment = departmentRepository.save(department).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the department
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, department.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departmentRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Department getPersistedDepartment(Department department) {
        return departmentRepository.findById(department.getId()).block();
    }

    protected void assertPersistedDepartmentToMatchAllProperties(Department expectedDepartment) {
        assertDepartmentAllPropertiesEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
    }

    protected void assertPersistedDepartmentToMatchUpdatableProperties(Department expectedDepartment) {
        assertDepartmentAllUpdatablePropertiesEquals(expectedDepartment, getPersistedDepartment(expectedDepartment));
    }
}
