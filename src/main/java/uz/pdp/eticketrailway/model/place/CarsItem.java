package uz.pdp.eticketrailway.model.place;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarsItem{

	@JsonProperty("owner")
	private Owner owner;

	@JsonProperty("sellingInternetForbidden")
	private Object sellingInternetForbidden;

	@JsonProperty("comissionFee")
	private int comissionFee;

	@JsonProperty("uz")
	private Object uz;

	@JsonProperty("passVok")
	private PassVok passVok;

	@JsonProperty("type")
	private String type;

	@JsonProperty("trainLetter")
	private String trainLetter;

	@JsonProperty("classServiceInt")
	private Object classServiceInt;

	@JsonProperty("ud")
	private Object ud;

	@JsonProperty("tariffService")
	private String tariffService;

	@JsonProperty("saleOnTwo")
	private Object saleOnTwo;

	@JsonProperty("carrier")
	private Carrier carrier;

	@JsonProperty("saleOnFour")
	private Object saleOnFour;

	@JsonProperty("modificators")
	private Object modificators;

	@JsonProperty("car")
	private List<CarItem> car;

	@JsonProperty("typeShow")
	private String typeShow;

	@JsonProperty("tariff")
	private int tariff;

	@JsonProperty("addSigns")
	private String addSigns;

	@JsonProperty("classService")
	private ClassService classService;

	@JsonProperty("tariff2")
	private Object tariff2;



}