package uz.pdp.eticketrailway.payload.station;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionItem{
	private Boolean fullDay;
	private String type;
	private String depDate;
}