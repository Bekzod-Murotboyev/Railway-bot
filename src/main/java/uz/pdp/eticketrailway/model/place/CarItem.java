package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarItem{

	@JsonProperty("selFood")
	private Object selFood;

	@JsonProperty("seatsSex")
	private Object seatsSex;

	@JsonProperty("typeOfCarSchema")
	private TypeOfCarSchema typeOfCarSchema;

	@JsonProperty("selBedding")
	private Object selBedding;

	@JsonProperty("elRegPossible")
	private ElRegPossible elRegPossible;

	@JsonProperty("payFood")
	private Object payFood;

	@JsonProperty("seats")
	private Seats seats;

	@JsonProperty("nonSmoking")
	private Object nonSmoking;

	@JsonProperty("number")
	private String number;

	@JsonProperty("places")
	private String places;

	@JsonProperty("station")
	private Object station;

	@JsonProperty("subType")
	private String subType;

	@JsonProperty("addFood")
	private Object addFood;

	@JsonProperty("designCar")
	private Object designCar;

	@JsonProperty("typePlaces")
	private Object typePlaces;

	@JsonProperty("swim")
	private Object swim;

	public Object getSelFood(){
		return selFood;
	}

	public Object getSeatsSex(){
		return seatsSex;
	}

	public TypeOfCarSchema getTypeOfCarSchema(){
		return typeOfCarSchema;
	}

	public Object getSelBedding(){
		return selBedding;
	}

	public ElRegPossible getElRegPossible(){
		return elRegPossible;
	}

	public Object getPayFood(){
		return payFood;
	}

	public Seats getSeats(){
		return seats;
	}

	public Object getNonSmoking(){
		return nonSmoking;
	}

	public String getNumber(){
		return number;
	}

	public String getPlaces(){
		return places;
	}

	public Object getStation(){
		return station;
	}

	public String getSubType(){
		return subType;
	}

	public Object getAddFood(){
		return addFood;
	}

	public Object getDesignCar(){
		return designCar;
	}

	public Object getTypePlaces(){
		return typePlaces;
	}

	public Object getSwim(){
		return swim;
	}
}