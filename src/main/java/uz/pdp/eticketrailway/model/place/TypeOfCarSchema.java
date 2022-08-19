package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeOfCarSchema{

	@JsonProperty("carNumber")
	private String carNumber;

	@JsonProperty("barndName")
	private String barndName;

	@JsonProperty("seatsCount")
	private Integer seatsCount;

	@JsonProperty("classService")
	private String classService;

}