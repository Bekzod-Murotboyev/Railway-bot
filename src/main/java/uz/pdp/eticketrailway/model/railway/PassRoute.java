package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class PassRoute{
	private String codeTo;
	private String from;
	private String codeFrom;
	private String to;
}