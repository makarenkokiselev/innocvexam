package com.pablo.innocvexam.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.pablo.innocvexam.entity.User;
import com.pablo.innocvexam.model.UserDto;
import com.pablo.innocvexam.util.UserUtil;

public class UserMapperTest {

	private static final Date BIRTH_DATE = UserUtil.parseDate("1988-03-04");

	private UserMapper userMapper = new UserMapper();

	@Test
	public void when_map_a_UserDto_then_return_a_User() {
		User actualUser = userMapper.dtoToEntity(
				UserDto.builder().userId(1).name("NAME").birthDate(UserUtil.parseDate("1988-03-04")).build());
		assertNotNull(actualUser);
		assertEquals((Integer) 1, actualUser.getUserId());
		assertEquals("NAME", actualUser.getName());
		assertTrue(BIRTH_DATE.compareTo(actualUser.getBirthDate()) == 0);
	}

	@Test
	public void when_map_a_UserDtoList_then_return_a_UserList() {
		List<User> actualUserList = userMapper.dtoListToEntityList(Arrays
				.asList(UserDto.builder().userId(1).name("NAME").birthDate(UserUtil.parseDate("1988-03-04")).build()));
		assertNotNull(actualUserList);
		assertTrue(actualUserList.size() == 1);
		assertEquals((Integer) 1, actualUserList.get(0).getUserId());
		assertEquals("NAME", actualUserList.get(0).getName());
		assertTrue(BIRTH_DATE.compareTo(actualUserList.get(0).getBirthDate()) == 0);
	}

}