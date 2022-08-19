package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartureTrain{

	@JsonProperty("date")
	private String date;

	public String getDate(){
		return date;
	}
}