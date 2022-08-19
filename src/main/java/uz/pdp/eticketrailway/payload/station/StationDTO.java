package uz.pdp.eticketrailway.payload.station;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO{
	private String stationFrom;
	private String stationTo;
	private List<DirectionItem> direction;
}