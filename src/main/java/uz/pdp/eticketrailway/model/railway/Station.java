package uz.pdp.eticketrailway.model.railway;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Station{
	private String reqExpressZK;
	private Object showWithoutPlaces;
	private String reqLocalSend;
	private boolean hasError;
	private Object errorResp;
	private String type;
	private String reqLocalRecv;
	private String reqAddress;
	private String reqExpressDateTime;
	private List<DirectionItem> direction;
}