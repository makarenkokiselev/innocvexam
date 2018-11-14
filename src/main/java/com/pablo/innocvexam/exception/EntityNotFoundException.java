package com.pablo.innocvexam.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1644461976250425358L;
	
	private static final String ENTITY_NOT_FOUND_ERROR_MESSAGE = "Entity was not found";
	
	@Override
	public String getMessage() {
		return ENTITY_NOT_FOUND_ERROR_MESSAGE;
	}
	
}
