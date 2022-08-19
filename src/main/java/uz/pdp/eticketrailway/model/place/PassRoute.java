package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassRoute{

	@JsonProperty("codeTo")
	private String codeTo;

	@JsonProperty("from")
	private String from;

	@JsonProperty("codeFrom")
	private String codeFrom;

	@JsonProperty("to")
	private String to;

	public String getCodeTo(){
		return codeTo;
	}

	public String getFrom(){
		return from;
	}

	public String getCodeFrom(){
		return codeFrom;
	}

	public String getTo(){
		return to;
	}
}