package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassVok{

	@JsonProperty("stationFrom")
	private StationFrom stationFrom;

	@JsonProperty("stationTo")
	private Object stationTo;

	public StationFrom getStationFrom(){
		return stationFrom;
	}

	public Object getStationTo(){
		return stationTo;
	}
}