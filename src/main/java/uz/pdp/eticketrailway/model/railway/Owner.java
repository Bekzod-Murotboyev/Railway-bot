package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class Owner{
	private Country country;
	private Railway railway;
	private String type;
}