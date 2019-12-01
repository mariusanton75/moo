package com.moo.exception;

public class CustomerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1461323472822290824L;

	public CustomerNotFoundException(String name) {
		super("Could not find customer " + name);
	}
	public CustomerNotFoundException(int id) {
		super("Could not find customer with id: " + id);
	}
}