package com.pablo.innocvexam.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface GeneralMapper<E,T> {
	
	public default List<T> entityListToDtoList(final List<E> entityList) {
		Objects.requireNonNull(entityList, "Entity list must not be null");
		return entityList.stream().map(this::entityToDto).collect(Collectors.toList());
	}
	
	public default List<E> dtoListToEntityList(final List<T> dtoList) {
		Objects.requireNonNull(dtoList, "Dto list must not be null");
		return dtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	public T entityToDto(E entity);
	
	public E dtoToEntity(T dto);
	
}