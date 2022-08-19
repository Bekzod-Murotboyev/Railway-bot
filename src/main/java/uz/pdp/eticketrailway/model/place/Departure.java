package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Departure{

	@JsonProperty("localTime")
	private String localTime;

	@JsonProperty("stop")
	private Object stop;

	@JsonProperty("time")
	private String time;

	@JsonProperty("localDate")
	private String localDate;

	public String getLocalTime(){
		return localTime;
	}

	public Object getStop(){
		return stop;
	}

	public String getTime(){
		return time;
	}

	public String getLocalDate(){
		return localDate;
	}
}