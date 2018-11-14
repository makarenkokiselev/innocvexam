package com.pablo.innocvexam.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablo.innocvexam.model.GetUserListResponse;
import com.pablo.innocvexam.model.SaveOrUpdateUserRequest;
import com.pablo.innocvexam.model.UserDto;
import com.pablo.innocvexam.service.UserService;
import com.pablo.innocvexam.service.UserServiceDefault;
import com.pablo.innocvexam.util.UserUtil;

public class UserRestControllerTest {

	private static final String USER_URL = "/user";

	private MockMvc mockMvc;
	private UserRestController userController;
	private UserService userService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void setUp() {
		userService = mock(UserServiceDefault.class);
		userController = new UserRestController(userService);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void when_invoke_userList_obtain_null_and_response_is_no_content() throws Exception {
		when(userService.userList()).thenReturn(null);
		mockMvc.perform(get(USER_URL)).andExpect(status().isNoContent());
	}

	@Test
	public void when_invoke_userList_obtain_empty_list_and_response_is_no_content() throws Exception {
		when(userService.userList()).thenReturn(GetUserListResponse.builder().users(Collections.emptyList()).build());
		mockMvc.perform(get(USER_URL)).andExpect(status().isNoContent());
	}

	@Test
	public void when_invoke_userList_obtain_non_empty_list_and_response_is_ok() throws Exception {
		when(userService.userList()).thenReturn(GetUserListResponse.builder().users(Arrays.asList(
				UserDto.builder().userId(1).name("ANTONIO").birthDate(UserUtil.parseDate("1987-04-12")).build()))
				.build());
		mockMvc.perform(get(USER_URL)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.users", Matchers.hasSize(1)))
				.andExpect(jsonPath("$.users[0].userId", Matchers.is(1)))
				.andExpect(jsonPath("$.users[0].name", Matchers.is("ANTONIO")))
				.andExpect(jsonPath("$.users[0].birthDate", Matchers.is("1987-04-12"))).andExpect(status().isOk());
		verify(userService).userList();
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void when_search_user_with_id_1_then_response_ok() throws Exception {
		when(userService.searchUserById(anyInt())).thenReturn(
				UserDto.builder().userId(1).name("ANTONIO").birthDate(UserUtil.parseDate("1987-04-12")).build());
		mockMvc.perform(get(USER_URL.concat("/{userId}"), 1))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.userId", Matchers.is(1))).andExpect(jsonPath("$.name", Matchers.is("ANTONIO")))
				.andExpect(jsonPath("$.birthDate", Matchers.is("1987-04-12"))).andExpect(status().isOk());
		verify(userService).searchUserById(anyInt());
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void when_create_a_new_user_then_response_created() throws Exception {
		doNothing().when(userService).createUser(any(SaveOrUpdateUserRequest.class));
		mockMvc.perform(post(USER_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(
						SaveOrUpdateUserRequest.builder().name("ANTONIO").birthDate("1987-04-12").build())))
				.andExpect(status().isCreated());
		verify(userService).createUser(any(SaveOrUpdateUserRequest.class));
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void when_update_a_user_then_response_ok() throws Exception {
		doNothing().when(userService).updateUser(anyInt(), any(SaveOrUpdateUserRequest.class));
		mockMvc.perform(put(USER_URL.concat("/{userId}"), 1).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsBytes(
						SaveOrUpdateUserRequest.builder().name("ANTONIO").birthDate("1987-04-12").build())))
				.andExpect(status().isOk());
		verify(userService).updateUser(anyInt(), any(SaveOrUpdateUserRequest.class));
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void when_delete_a_user_then_response_ok() throws Exception {
		doNothing().when(userService).removeUser(anyInt());
		mockMvc.perform(delete(USER_URL.concat("/{userId}"), 1)).andExpect(status().isOk());
		verify(userService).removeUser(anyInt());
		verifyNoMoreInteractions(userService);
	}

}