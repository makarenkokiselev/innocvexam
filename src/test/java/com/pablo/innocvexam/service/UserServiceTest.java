package com.pablo.innocvexam.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.pablo.innocvexam.entity.User;
import com.pablo.innocvexam.exception.EntityNotFoundException;
import com.pablo.innocvexam.mapper.UserMapper;
import com.pablo.innocvexam.model.GetUserListResponse;
import com.pablo.innocvexam.model.SaveOrUpdateUserRequest;
import com.pablo.innocvexam.model.UserDto;
import com.pablo.innocvexam.repository.UserRepository;
import com.pablo.innocvexam.util.UserUtil;

public class UserServiceTest {

	private static final Date BIRTH_DATE = UserUtil.parseDate("1987-04-03");

	private UserService userService;
	private UserRepository userRepository;
	private UserMapper userMapper;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userMapper = new UserMapper();
		userService = new UserServiceDefault(userRepository, userMapper);
	}

	@Test
	public void when_look_for_all_users_then_return_all_users() {
		when(userRepository.findAll())
				.thenReturn(Arrays.asList(User.builder().userId(1).name("NAME").birthDate(BIRTH_DATE).build()));
		GetUserListResponse actualResponse = userService.userList();
		assertNotNull(actualResponse);
		assertNotNull(actualResponse.getUsers());
		assertTrue(actualResponse.getUsers().size() == 1);
		assertEquals((Integer) 1, actualResponse.getUsers().get(0).getUserId());
		assertEquals("NAME", actualResponse.getUsers().get(0).getName());
		assertTrue(BIRTH_DATE.compareTo(actualResponse.getUsers().get(0).getBirthDate()) == 0);
		verify(userRepository).findAll();
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void when_create_a_user_then_do_nothing() {
		when(userRepository.save(any(User.class))).thenReturn(User.builder().build());
		userService.createUser(SaveOrUpdateUserRequest.builder().name("NAME").birthDate("1988-09-08").build());
		verify(userRepository).save(any(User.class));
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void when_look_for_a_user_by_id_then_return_the_user() {
		when(userRepository.findById(anyInt()))
				.thenReturn(Optional.ofNullable(User.builder().userId(1).name("NAME").birthDate(BIRTH_DATE).build()));
		UserDto actualResponse = userService.searchUserById(1);
		assertNotNull(actualResponse);
		assertEquals((Integer) 1, actualResponse.getUserId());
		assertEquals("NAME", actualResponse.getName());
		assertTrue(BIRTH_DATE.compareTo(actualResponse.getBirthDate()) == 0);
		verify(userRepository).findById(anyInt());
		verifyNoMoreInteractions(userRepository);
	}

	@Test(expected = EntityNotFoundException.class)
	public void when_look_for_a_user_by_id_then_throw_EntityNotFoundException() {
		when(userRepository.findById(anyInt())).thenThrow(EntityNotFoundException.class);
		userService.searchUserById(1);
	}

	@Test
	public void when_update_a_user_then_do_nothing() {
		when(userRepository.findById(anyInt()))
				.thenReturn(Optional.ofNullable(User.builder().userId(1).name("NAME").birthDate(BIRTH_DATE).build()));
		doNothing().when(userRepository).flush();
		userService.updateUser(1, SaveOrUpdateUserRequest.builder().name("NAME").birthDate("1988-09-08").build());
		verify(userRepository).findById(anyInt());
		verify(userRepository).flush();
		verifyNoMoreInteractions(userRepository);
	}

	@Test(expected = EntityNotFoundException.class)
	public void when_update_a_user_then_throw_EntityNotFoundException() {
		when(userRepository.findById(anyInt())).thenThrow(EntityNotFoundException.class);
		userService.updateUser(1, SaveOrUpdateUserRequest.builder().build());
	}

	@Test
	public void when_remove_a_user_then_do_nothing() {
		when(userRepository.findById(anyInt()))
				.thenReturn(Optional.ofNullable(User.builder().userId(1).name("NAME").birthDate(BIRTH_DATE).build()));
		doNothing().when(userRepository).delete(any(User.class));
		userService.removeUser(1);
		verify(userRepository).findById(anyInt());
		verify(userRepository).delete(any(User.class));
		verifyNoMoreInteractions(userRepository);
	}

	@Test(expected = EntityNotFoundException.class)
	public void when_remove_a_user_then_throw_EntityNotFoundException() {
		when(userRepository.findById(anyInt())).thenThrow(EntityNotFoundException.class);
		userService.removeUser(1);
	}

}