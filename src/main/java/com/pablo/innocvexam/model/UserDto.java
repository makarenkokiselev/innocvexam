package com.pablo.innocvexam.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UserDto {
	
	@ApiModelProperty(notes="Identificador de usuario")
	private Integer userId;
	@ApiModelProperty(notes="Nombre del usuario", example="Manuel")
	private String name;
	@ApiModelProperty(notes="Fecha de nacimiento del usuario", example="1980-10-27")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birthDate;
	
}