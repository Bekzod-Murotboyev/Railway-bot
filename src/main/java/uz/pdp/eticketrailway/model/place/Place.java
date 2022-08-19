package uz.pdp.eticketrailway.model.place;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Place{

	@JsonProperty("reqExpressZK")
	private String reqExpressZK;

	@JsonProperty("showWithoutPlaces")
	private Object showWithoutPlaces;

	@JsonProperty("reqLocalSend")
	private String reqLocalSend;

	@JsonProperty("hasError")
	private boolean hasError;

	@JsonProperty("errorResp")
	private Object errorResp;

	@JsonProperty("type")
	private String type;

	@JsonProperty("direction")
	private List<DirectionItem> direction;

	@JsonProperty("reqLocalRecv")
	private String reqLocalRecv;

	@JsonProperty("reqAddress")
	private String reqAddress;

	@JsonProperty("reqExpressDateTime")
	private String reqExpressDateTime;

	public String getReqExpressZK(){
		return reqExpressZK;
	}

	public Object getShowWithoutPlaces(){
		return showWithoutPlaces;
	}

	public String getReqLocalSend(){
		return reqLocalSend;
	}

	public boolean isHasError(){
		return hasError;
	}

	public Object getErrorResp(){
		return errorResp;
	}

	public String getType(){
		return type;
	}

	public List<DirectionItem> getDirection(){
		return direction;
	}

	public String getReqLocalRecv(){
		return reqLocalRecv;
	}

	public String getReqAddress(){
		return reqAddress;
	}

	public String getReqExpressDateTime(){
		return reqExpressDateTime;
	}
}