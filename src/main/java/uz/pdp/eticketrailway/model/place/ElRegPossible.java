package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ElRegPossible{

	@JsonProperty("uk")
	private Object uk;

	@JsonProperty("akp")
	private Object akp;

	public Object getUk(){
		return uk;
	}

	public Object getAkp(){
		return akp;
	}
}