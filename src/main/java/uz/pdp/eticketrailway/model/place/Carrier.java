package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Carrier{

	@JsonProperty("name")
	private String name;

	public String getName(){
		return name;
	}
}