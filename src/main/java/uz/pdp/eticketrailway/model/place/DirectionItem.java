package uz.pdp.eticketrailway.model.place;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectionItem{

	@JsonProperty("hasError")
	private boolean hasError;

	@JsonProperty("errorResp")
	private Object errorResp;

	@JsonProperty("type")
	private String type;

	@JsonProperty("passRoute")
	private PassRoute passRoute;

	@JsonProperty("trains")
	private List<TrainsItem> trains;

	public boolean isHasError(){
		return hasError;
	}

	public Object getErrorResp(){
		return errorResp;
	}

	public String getType(){
		return type;
	}

	public PassRoute getPassRoute(){
		return passRoute;
	}

	public List<TrainsItem> getTrains(){
		return trains;
	}
}