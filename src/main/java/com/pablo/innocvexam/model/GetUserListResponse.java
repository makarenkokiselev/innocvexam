package com.pablo.innocvexam.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserListResponse {
	
	@ApiModelProperty(notes = "Listado de usuarios")
	private List<UserDto> users;
	
}