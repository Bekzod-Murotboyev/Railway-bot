package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainsItem{

	@JsonProperty("date")
	private String date;

	@JsonProperty("train")
	private Train train;

	public String getDate(){
		return date;
	}

	public Train getTrain(){
		return train;
	}
}