package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassService{

	@JsonProperty("type")
	private String type;

	@JsonProperty("content")
	private String content;

	public String getType(){
		return type;
	}

	public String getContent(){
		return content;
	}
}