package uz.pdp.eticketrailway.model.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seats{

	private int seatsLateralUp;

	private int seatsLateralDn;

	private int seatsUp;

	private int seatsDn;

	private int freeComp;

	private int seatsUndef;

}