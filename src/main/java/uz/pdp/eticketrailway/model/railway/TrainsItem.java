package uz.pdp.eticketrailway.model.railway;

import java.util.List;
import lombok.Data;

@Data
public class TrainsItem{
	private String date;
	private List<TrainItem> train;
}