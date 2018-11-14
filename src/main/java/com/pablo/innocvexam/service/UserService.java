package com.pablo.innocvexam.service;

import com.pablo.innocvexam.model.GetUserListResponse;
import com.pablo.innocvexam.model.SaveOrUpdateUserRequest;
import com.pablo.innocvexam.model.UserDto;

public interface UserService {

	GetUserListResponse userList();

	void createUser(SaveOrUpdateUserRequest request);

	UserDto searchUserById(Integer userId);

	void updateUser(Integer userId, SaveOrUpdateUserRequest request);

	void removeUser(Integer userId);

}
