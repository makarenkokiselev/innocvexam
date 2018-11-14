package com.pablo.innocvexam.service;

import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pablo.innocvexam.entity.User;
import com.pablo.innocvexam.exception.EntityNotFoundException;
import com.pablo.innocvexam.mapper.UserMapper;
import com.pablo.innocvexam.model.GetUserListResponse;
import com.pablo.innocvexam.model.SaveOrUpdateUserRequest;
import com.pablo.innocvexam.model.UserDto;
import com.pablo.innocvexam.repository.UserRepository;
import com.pablo.innocvexam.util.UserUtil;

@Service
public class UserServiceDefault implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	
	@Autowired
	public UserServiceDefault(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	@Override
	public GetUserListResponse userList() {
		return GetUserListResponse.builder()
				.users(userMapper.entityListToDtoList(userRepository.findAll()))
				.build();
	}
	
	@Override
	public void createUser(SaveOrUpdateUserRequest request) {
		Objects.requireNonNull(request, "Request must not be null");
		Function<SaveOrUpdateUserRequest, User> saveOrUpdateMapper = 
				createRequest -> User.builder()
									.name(createRequest.getName())
									.birthDate(UserUtil.parseDate(createRequest.getBirthDate()))
									.build();
		User user = saveOrUpdateMapper.apply(request);
		userRepository.save(user);
	}

	@Override
	public UserDto searchUserById(Integer userId) {
		Objects.requireNonNull(userId, "UserId must not be null");
		return userMapper.entityToDto(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
	}

	@Override
	public void updateUser(Integer userId, SaveOrUpdateUserRequest request) {
		Objects.requireNonNull(userId, "UserId must not be null");
		Objects.requireNonNull(request, "Request must not be null");
		User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
		user.setName(request.getName());
		user.setBirthDate(UserUtil.parseDate(request.getBirthDate()));
		userRepository.flush();
	}

	@Override
	public void removeUser(Integer userId) {
		Objects.requireNonNull(userId, "UserId must not be null");
		User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
		userRepository.delete(user);
	}

}
