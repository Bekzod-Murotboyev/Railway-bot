package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner{

	@JsonProperty("country")
	private Country country;

	@JsonProperty("railway")
	private Railway railway;

	@JsonProperty("type")
	private String type;

	public Country getCountry(){
		return country;
	}

	public Railway getRailway(){
		return railway;
	}

	public String getType(){
		return type;
	}
}