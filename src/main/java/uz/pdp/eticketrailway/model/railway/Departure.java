package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class Departure{
	private String localTime;
	private Object stop;
	private String time;
	private String localDate;
}