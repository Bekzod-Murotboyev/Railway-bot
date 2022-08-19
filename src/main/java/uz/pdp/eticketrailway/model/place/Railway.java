package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Railway{

	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	private String name;

	public String getCode(){
		return code;
	}

	public String getName(){
		return name;
	}
}