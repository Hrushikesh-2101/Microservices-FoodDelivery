package com.foodSystem.user_service.controller;

import com.foodSystem.user_service.entity.User;
import com.foodSystem.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @Test
   void testGetAllUsers() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Page<User> users = new PageImpl<>(Collections.singletonList(user));
    Mockito.when(userService.getAllUsers(any(Pageable.class), any(), any(Sort.class)))
        .thenReturn(users);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/users")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("sortDir", "asc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetUserById() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/users/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testCreateUser() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password\", \"phone\": \"1234567890\", \"address\": \"123 Main St\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testUpdateUser() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    Mockito.when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password\", \"phone\": \"1234567890\", \"address\": \"123 Main St\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testDeleteUser() throws Exception {
    Mockito.doNothing().when(userService).deleteUser(anyLong());

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")).andExpect(status().isOk());
  }
}
