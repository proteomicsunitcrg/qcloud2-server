package eu.qcloud.exceptions;

import org.springframework.dao.DataRetrievalFailureException;

public class NotFoundException extends DataRetrievalFailureException{

	public NotFoundException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}


}
	