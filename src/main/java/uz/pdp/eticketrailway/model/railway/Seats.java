package uz.pdp.eticketrailway.model.railway;

import lombok.Data;

@Data
public class Seats{
	private Object seatsLateralUp;
	private Object seatsLateralDn;
	private Object seatsUp;
	private Object seatsDn;
	private Object freeComp;
	private String seatsUndef;
}