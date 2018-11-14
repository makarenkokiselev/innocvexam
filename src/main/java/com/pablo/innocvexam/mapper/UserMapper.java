package com.pablo.innocvexam.mapper;
import java.util.Objects;

import org.springframework.stereotype.Component;
import com.pablo.innocvexam.entity.User;
import com.pablo.innocvexam.model.UserDto;

@Component
public class UserMapper implements GeneralMapper<User, UserDto> {

	@Override
	public UserDto entityToDto(User entity) {
		Objects.requireNonNull(entity, "Entity must not be null");
		return UserDto.builder()
				.userId(entity.getUserId())
				.name(entity.getName())
				.birthDate(entity.getBirthDate()).build();
	}

	@Override
	public User dtoToEntity(UserDto dto) {
		Objects.requireNonNull(dto, "Dto must not be null");
		return User.builder()
				.userId(dto.getUserId())
				.name(dto.getName())
				.birthDate(dto.getBirthDate()).build();
	}

}
