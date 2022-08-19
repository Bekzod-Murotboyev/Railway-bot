package uz.pdp.eticketrailway.payload.place;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceDTO{

	@SerializedName("detailNumPlaces")
	private int detailNumPlaces;

	@SerializedName("stationFrom")
	private String stationFrom;

	@SerializedName("stationTo")
	private String stationTo;

	@SerializedName("direction")
	private List<DirectionItem> direction;

	public int getDetailNumPlaces(){
		return detailNumPlaces;
	}

	public String getStationFrom(){
		return stationFrom;
	}

	public String getStationTo(){
		return stationTo;
	}

	public List<DirectionItem> getDirection(){
		return direction;
	}
}