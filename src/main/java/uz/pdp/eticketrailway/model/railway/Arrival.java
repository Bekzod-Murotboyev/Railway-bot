package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class Arrival{
	private String date;
	private String localTime;
	private Object stop;
	private String time;
	private String localDate;
}