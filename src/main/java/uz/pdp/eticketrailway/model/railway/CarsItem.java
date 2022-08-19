package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class CarsItem{
	private String indexType;
	private String typeShow;
	private String freeSeats;
	private String type;
	private Seats seats;
	private Tariffs tariffs;
}