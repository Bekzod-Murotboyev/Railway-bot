package uz.pdp.eticketrailway.payload.place;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DirectionItem{

	@SerializedName("fullDay")
	private boolean fullDay;

	@SerializedName("type")
	private String type;

	@SerializedName("depDate")
	private String depDate;

	@SerializedName("train")
	private Train train;

}