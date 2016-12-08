package nz.co.thescene.client.errors;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class Argument {
	
	private String[] codes;
	
	private String[] arguments;
	
	private String defaultMessage;
	
	private String code;
	
	

}
