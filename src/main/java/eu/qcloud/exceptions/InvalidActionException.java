package eu.qcloud.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class InvalidActionException extends DataIntegrityViolationException{

	private static final long serialVersionUID = 1L;

	public InvalidActionException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}


}
	