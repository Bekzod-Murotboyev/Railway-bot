package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationFrom{

	@JsonProperty("code")
	private String code;

	public String getCode(){
		return code;
	}
}