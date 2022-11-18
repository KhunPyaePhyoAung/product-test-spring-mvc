package me.khun.productmvc.ui;

public class Alert {

	public enum Status {
		SUCCESS, ERROR, WARNING, INFO
	}

	private String message;
	private Status status;

	public Alert() {
		
	}
	
	public Alert(String message, Status status) {
		this.message = message;
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String messag) {
		this.message = messag;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
