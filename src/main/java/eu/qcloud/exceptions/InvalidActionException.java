package eu.qcloud.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class InvalidActionException extends DataIntegrityViolationException{

	public InvalidActionException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}


}
	