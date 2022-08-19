package uz.pdp.eticketrailway.model.place;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route{

	@JsonProperty("station")
	private List<String> station;

	public List<String> getStation(){
		return station;
	}
}