package com.pablo.innocvexam.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pablo.innocvexam.model.GetUserListResponse;
import com.pablo.innocvexam.model.SaveOrUpdateUserRequest;
import com.pablo.innocvexam.model.UserDto;
import com.pablo.innocvexam.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value="Api para gestionar los usuarios en base de datos")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "Obtiene todos los usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Petición correcta"),
			@ApiResponse(code = 204, message = "Sin resultado") })
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserListResponse> getUsers() {
		GetUserListResponse response = userService.userList();
		if (response == null || CollectionUtils.isEmpty(response.getUsers())) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Crea un nuevo usuario", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 201, message = "Creado"), @ApiResponse(code = 400, message = "Petición malformada") })
	@PostMapping(value = "/user")
	public ResponseEntity<Void> postUser(@RequestBody @Valid SaveOrUpdateUserRequest request) {
		userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "Obtiene el usuario con id {userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Petición correcta"),
			@ApiResponse(code = 204, message = "Sin resultado") })
	@GetMapping(value = "/user/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
		UserDto response = userService.searchUserById(userId);
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "Actualiza el usuario con id {userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Petición correcta"),
			@ApiResponse(code = 204, message = "Sin resultado"), @ApiResponse(code = 400, message = "Petición malformada") })
	@PutMapping(value = "/user/{userId}")
	public ResponseEntity<Void> putUser(@PathVariable Integer userId, @RequestBody SaveOrUpdateUserRequest request) {
		userService.updateUser(userId, request);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Elimina el usuario con id {userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Petición correcta"),
			@ApiResponse(code = 204, message = "Sin resultado") })
	@DeleteMapping(value = "/user/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
		userService.removeUser(userId);
		return ResponseEntity.ok().build();
	}

}