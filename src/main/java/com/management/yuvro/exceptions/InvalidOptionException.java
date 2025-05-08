package com.management.yuvro.exceptions;

public class InvalidOptionException extends RuntimeException {
  
	private static final long serialVersionUID = 1L;

	public InvalidOptionException(String message) {
        super(message);
    }
}
