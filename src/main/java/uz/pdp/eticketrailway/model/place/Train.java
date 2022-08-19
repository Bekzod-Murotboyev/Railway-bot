package uz.pdp.eticketrailway.model.place;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Train{

	@JsonProperty("bus")
	private Object bus;

	@JsonProperty("comments")
	private Object comments;

	@JsonProperty("elRegPossible")
	private String elRegPossible;

	@JsonProperty("arrival")
	private Arrival arrival;

	@JsonProperty("parom")
	private Object parom;

	@JsonProperty("length")
	private String length;

	@JsonProperty("timeInWay")
	private String timeInWay;

	@JsonProperty("number2")
	private String number2;

	@JsonProperty("trainBrandName")
	private String trainBrandName;

	@JsonProperty("type")
	private String type;

	@JsonProperty("trainSchemaType")
	private String trainSchemaType;

	@JsonProperty("number")
	private String number;

	@JsonProperty("cars")
	private List<CarsItem> cars;

	@JsonProperty("route")
	private Route route;

	@JsonProperty("firmName")
	private Object firmName;

	@JsonProperty("departure")
	private Departure departure;

	@JsonProperty("brand")
	private Object brand;

	@JsonProperty("departureTrain")
	private DepartureTrain departureTrain;

	public Object getBus(){
		return bus;
	}

	public Object getComments(){
		return comments;
	}

	public String getElRegPossible(){
		return elRegPossible;
	}

	public Arrival getArrival(){
		return arrival;
	}

	public Object getParom(){
		return parom;
	}

	public String getLength(){
		return length;
	}

	public String getTimeInWay(){
		return timeInWay;
	}

	public String getNumber2(){
		return number2;
	}

	public String getTrainBrandName(){
		return trainBrandName;
	}

	public String getType(){
		return type;
	}

	public String getTrainSchemaType(){
		return trainSchemaType;
	}

	public String getNumber(){
		return number;
	}

	public List<CarsItem> getCars(){
		return cars;
	}

	public Route getRoute(){
		return route;
	}

	public Object getFirmName(){
		return firmName;
	}

	public Departure getDeparture(){
		return departure;
	}

	public Object getBrand(){
		return brand;
	}

	public DepartureTrain getDepartureTrain(){
		return departureTrain;
	}
}