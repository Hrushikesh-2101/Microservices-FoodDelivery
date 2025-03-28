package com.foodSystem.user_service.controller;

import com.foodSystem.user_service.entity.User;
import com.foodSystem.user_service.repository.UserRepository;
import com.foodSystem.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  private int port;

  @MockBean
  private TestRestTemplate restTemplate;

  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  private String baseUrl;

  @BeforeEach
  void setUp() {
    baseUrl = "http://localhost:" + port + "/api/users";
    userRepository.deleteAll();
  }

  @Test
  void testGetAllUsers() {
    User user = new User();
    user.setName("John Doe");
    userRepository.save(user);

    ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl, User[].class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
  }

  @Test
  void testGetUserById() {
    User user = new User();
    user.setName("John Doe");
    user = userRepository.save(user);

    ResponseEntity<User> response =
        restTemplate.getForEntity(baseUrl + "/" + user.getId(), User.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getName()).isEqualTo("John Doe");
  }

  @Test
  void testCreateUser() {
    User user = new User();
    user.setName("John Doe");
    user.setEmail("john.doe@example.com");
    user.setPassword("password");
    user.setPhone("1234567890");
    user.setAddress("123 Main St");

    ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, user, User.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getName()).isEqualTo("John Doe");
  }

  @Test
  void testUpdateUser() {
    User user = new User();
    user.setName("John Doe");
    user = userRepository.save(user);

    user.setName("Jane Doe");
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<User> entity = new HttpEntity<>(user, headers);

    ResponseEntity<User> response =
        restTemplate.exchange(baseUrl + "/" + user.getId(), HttpMethod.PUT, entity, User.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
  }

  @Test
  void testDeleteUser() {
    User user = new User();
    user.setName("John Doe");
    user = userRepository.save(user);

    restTemplate.delete(baseUrl + "/" + user.getId());

    ResponseEntity<User> response =
        restTemplate.getForEntity(baseUrl + "/" + user.getId(), User.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void testGetAllUsersWithFilters() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Page<User> users = new PageImpl<>(Collections.singletonList(user));
    Mockito.when(userService.getAllUsers(any(Pageable.class), any(), any(Sort.class))).thenReturn(users);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                    .param("name", "John")
                    .param("email", "john.doe@example.com")
                    .param("phone", "1234567890")
                    .param("address", "123 Main St")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortBy", "name")
                    .param("sortDir", "asc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetAllUsersWithSorting() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Page<User> users = new PageImpl<>(Collections.singletonList(user));
    Mockito.when(userService.getAllUsers(any(Pageable.class), any(), any(Sort.class))).thenReturn(users);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                    .param("sortBy", "name")
                    .param("sortDir", "asc")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetAllUsersWithPagination() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Page<User> users = new PageImpl<>(Collections.singletonList(user));
    Mockito.when(userService.getAllUsers(any(Pageable.class), any(), any(Sort.class))).thenReturn(users);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

}
