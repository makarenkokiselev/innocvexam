package com.pablo.innocvexam.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pablo.innocvexam.model.validator.DateConstraint;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveOrUpdateUserRequest {
	
	@NotNull
	@Size(max=50, message="Name has a maximum size of 50 characters")
	@ApiModelProperty(notes="Nombre del usuario", example="Antonio")
	private String name;
	
	@NotNull
	@DateConstraint
	@ApiModelProperty(notes="Fecha de nacimiento del usuario", example="1980-05-14")
	private String birthDate;
	
}