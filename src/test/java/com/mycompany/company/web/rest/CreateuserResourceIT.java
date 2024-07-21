package com.mycompany.company.web.rest;

import static com.mycompany.company.domain.CreateuserAsserts.*;
import static com.mycompany.company.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.company.IntegrationTest;
import com.mycompany.company.domain.Createuser;
import com.mycompany.company.domain.enumeration.role;
import com.mycompany.company.domain.enumeration.status;
import com.mycompany.company.repository.CreateuserRepository;
import java.util.Base64;
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
 * Integration tests for the {@link CreateuserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CreateuserResourceIT {

    private static final String DEFAULT_ROLL_NO = "AAAAAAAAAA";
    private static final String UPDATED_ROLL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_USER_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_USER_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_USER_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_USER_IMAGE_CONTENT_TYPE = "image/png";

    private static final status DEFAULT_ROLE_STATUS = status.ACTIVE;
    private static final status UPDATED_ROLE_STATUS = status.ACTIVE;

    private static final role DEFAULT_ROLE = role.ROLE_USER;
    private static final role UPDATED_ROLE = role.ROLE_USER;

    private static final String ENTITY_API_URL = "/api/createusers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CreateuserRepository createuserRepository;

    @Autowired
    private WebTestClient webTestClient;

    private Createuser createuser;

    private Createuser insertedCreateuser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Createuser createEntity() {
        Createuser createuser = new Createuser()
            .rollNo(DEFAULT_ROLL_NO)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD)
            .department(DEFAULT_DEPARTMENT)
            .designation(DEFAULT_DESIGNATION)
            .email(DEFAULT_EMAIL)
            .userImage(DEFAULT_USER_IMAGE)
            .userImageContentType(DEFAULT_USER_IMAGE_CONTENT_TYPE)
            .roleStatus(DEFAULT_ROLE_STATUS)
            .role(DEFAULT_ROLE);
        return createuser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Createuser createUpdatedEntity() {
        Createuser createuser = new Createuser()
            .rollNo(UPDATED_ROLL_NO)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .department(UPDATED_DEPARTMENT)
            .designation(UPDATED_DESIGNATION)
            .email(UPDATED_EMAIL)
            .userImage(UPDATED_USER_IMAGE)
            .userImageContentType(UPDATED_USER_IMAGE_CONTENT_TYPE)
            .roleStatus(UPDATED_ROLE_STATUS)
            .role(UPDATED_ROLE);
        return createuser;
    }

    @BeforeEach
    public void initTest() {
        createuser = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCreateuser != null) {
            createuserRepository.delete(insertedCreateuser).block();
            insertedCreateuser = null;
        }
    }

    @Test
    void createCreateuser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Createuser
        var returnedCreateuser = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Createuser.class)
            .returnResult()
            .getResponseBody();

        // Validate the Createuser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCreateuserUpdatableFieldsEquals(returnedCreateuser, getPersistedCreateuser(returnedCreateuser));

        insertedCreateuser = returnedCreateuser;
    }

    @Test
    void createCreateuserWithExistingId() throws Exception {
        // Create the Createuser with an existing ID
        createuser.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkRollNoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setRollNo(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkUserNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setUserName(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPasswordIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setPassword(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDepartmentIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setDepartment(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDesignationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setDesignation(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setEmail(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkRoleStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setRoleStatus(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkRoleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        createuser.setRole(null);

        // Create the Createuser, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCreateusers() {
        // Initialize the database
        insertedCreateuser = createuserRepository.save(createuser).block();

        // Get all the createuserList
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
            .value(hasItem(createuser.getId()))
            .jsonPath("$.[*].rollNo")
            .value(hasItem(DEFAULT_ROLL_NO))
            .jsonPath("$.[*].userName")
            .value(hasItem(DEFAULT_USER_NAME))
            .jsonPath("$.[*].password")
            .value(hasItem(DEFAULT_PASSWORD))
            .jsonPath("$.[*].department")
            .value(hasItem(DEFAULT_DEPARTMENT))
            .jsonPath("$.[*].designation")
            .value(hasItem(DEFAULT_DESIGNATION))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL))
            .jsonPath("$.[*].userImageContentType")
            .value(hasItem(DEFAULT_USER_IMAGE_CONTENT_TYPE))
            .jsonPath("$.[*].userImage")
            .value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_USER_IMAGE)))
            .jsonPath("$.[*].roleStatus")
            .value(hasItem(DEFAULT_ROLE_STATUS.toString()))
            .jsonPath("$.[*].role")
            .value(hasItem(DEFAULT_ROLE.toString()));
    }

    @Test
    void getCreateuser() {
        // Initialize the database
        insertedCreateuser = createuserRepository.save(createuser).block();

        // Get the createuser
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, createuser.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(createuser.getId()))
            .jsonPath("$.rollNo")
            .value(is(DEFAULT_ROLL_NO))
            .jsonPath("$.userName")
            .value(is(DEFAULT_USER_NAME))
            .jsonPath("$.password")
            .value(is(DEFAULT_PASSWORD))
            .jsonPath("$.department")
            .value(is(DEFAULT_DEPARTMENT))
            .jsonPath("$.designation")
            .value(is(DEFAULT_DESIGNATION))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL))
            .jsonPath("$.userImageContentType")
            .value(is(DEFAULT_USER_IMAGE_CONTENT_TYPE))
            .jsonPath("$.userImage")
            .value(is(Base64.getEncoder().encodeToString(DEFAULT_USER_IMAGE)))
            .jsonPath("$.roleStatus")
            .value(is(DEFAULT_ROLE_STATUS.toString()))
            .jsonPath("$.role")
            .value(is(DEFAULT_ROLE.toString()));
    }

    @Test
    void getNonExistingCreateuser() {
        // Get the createuser
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCreateuser() throws Exception {
        // Initialize the database
        insertedCreateuser = createuserRepository.save(createuser).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the createuser
        Createuser updatedCreateuser = createuserRepository.findById(createuser.getId()).block();
        updatedCreateuser
            .rollNo(UPDATED_ROLL_NO)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .department(UPDATED_DEPARTMENT)
            .designation(UPDATED_DESIGNATION)
            .email(UPDATED_EMAIL)
            .userImage(UPDATED_USER_IMAGE)
            .userImageContentType(UPDATED_USER_IMAGE_CONTENT_TYPE)
            .roleStatus(UPDATED_ROLE_STATUS)
            .role(UPDATED_ROLE);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedCreateuser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedCreateuser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCreateuserToMatchAllProperties(updatedCreateuser);
    }

    @Test
    void putNonExistingCreateuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        createuser.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, createuser.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCreateuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        createuser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCreateuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        createuser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCreateuserWithPatch() throws Exception {
        // Initialize the database
        insertedCreateuser = createuserRepository.save(createuser).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the createuser using partial update
        Createuser partialUpdatedCreateuser = new Createuser();
        partialUpdatedCreateuser.setId(createuser.getId());

        partialUpdatedCreateuser
            .designation(UPDATED_DESIGNATION)
            .userImage(UPDATED_USER_IMAGE)
            .userImageContentType(UPDATED_USER_IMAGE_CONTENT_TYPE)
            .roleStatus(UPDATED_ROLE_STATUS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCreateuser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCreateuser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Createuser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCreateuserUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCreateuser, createuser),
            getPersistedCreateuser(createuser)
        );
    }

    @Test
    void fullUpdateCreateuserWithPatch() throws Exception {
        // Initialize the database
        insertedCreateuser = createuserRepository.save(createuser).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the createuser using partial update
        Createuser partialUpdatedCreateuser = new Createuser();
        partialUpdatedCreateuser.setId(createuser.getId());

        partialUpdatedCreateuser
            .rollNo(UPDATED_ROLL_NO)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD)
            .department(UPDATED_DEPARTMENT)
            .designation(UPDATED_DESIGNATION)
            .email(UPDATED_EMAIL)
            .userImage(UPDATED_USER_IMAGE)
            .userImageContentType(UPDATED_USER_IMAGE_CONTENT_TYPE)
            .roleStatus(UPDATED_ROLE_STATUS)
            .role(UPDATED_ROLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCreateuser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCreateuser))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Createuser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCreateuserUpdatableFieldsEquals(partialUpdatedCreateuser, getPersistedCreateuser(partialUpdatedCreateuser));
    }

    @Test
    void patchNonExistingCreateuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        createuser.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, createuser.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCreateuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        createuser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, UUID.randomUUID().toString())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCreateuser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        createuser.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(createuser))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Createuser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCreateuser() {
        // Initialize the database
        insertedCreateuser = createuserRepository.save(createuser).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the createuser
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, createuser.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return createuserRepository.count().block();
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

    protected Createuser getPersistedCreateuser(Createuser createuser) {
        return createuserRepository.findById(createuser.getId()).block();
    }

    protected void assertPersistedCreateuserToMatchAllProperties(Createuser expectedCreateuser) {
        assertCreateuserAllPropertiesEquals(expectedCreateuser, getPersistedCreateuser(expectedCreateuser));
    }

    protected void assertPersistedCreateuserToMatchUpdatableProperties(Createuser expectedCreateuser) {
        assertCreateuserAllUpdatablePropertiesEquals(expectedCreateuser, getPersistedCreateuser(expectedCreateuser));
    }
}
