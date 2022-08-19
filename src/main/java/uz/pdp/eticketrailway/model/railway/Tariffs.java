package uz.pdp.eticketrailway.model.railway;

import java.util.List;
import lombok.Data;

@Data
public class Tariffs{
	private List<TariffItem> tariff;
}