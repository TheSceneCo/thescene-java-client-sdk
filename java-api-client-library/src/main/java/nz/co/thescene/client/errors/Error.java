package nz.co.thescene.client.errors;

import lombok.Data;

@Data
public class Error {

	private String[] codes;

	// FIXME: Figure out how to parse this. 
	// See stackoverflow question http://stackoverflow.com/questions/37281147/parsing-multi-value-type-json-array-with-gson
	//private Argument[] arguments;

	private String defaultMessage;

	private String objectName;
	
	private String code;

	private String field;

	private String rejectedValue;

	private String bindingFailure;
}
