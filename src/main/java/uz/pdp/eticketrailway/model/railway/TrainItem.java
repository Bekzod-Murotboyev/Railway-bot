package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class TrainItem{
	private Object bus;
	private Object comments;
	private String elRegPossible;
	private Arrival arrival;
	private Object parom;
	private String length;
	private String timeInWay;
	private String number2;
	private String type;
	private String number;
	private Places places;
	private Route route;
	private Object firmName;
	private Departure departure;
	private DepartureTrain departureTrain;
	private String brand;
}