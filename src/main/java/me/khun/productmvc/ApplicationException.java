package me.khun.productmvc;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ApplicationException() {}
	
	public ApplicationException(String message) {
		super(message);
	}

}
