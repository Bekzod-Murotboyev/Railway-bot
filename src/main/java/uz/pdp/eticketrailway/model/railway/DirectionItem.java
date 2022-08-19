package uz.pdp.eticketrailway.model.railway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionItem{
	private Object notAllTrains;
	private String type;
	private PassRoute passRoute;
	private List<TrainsItem> trains;
}