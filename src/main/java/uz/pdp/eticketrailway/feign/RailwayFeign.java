package uz.pdp.eticketrailway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import uz.pdp.eticketrailway.model.place.Place;
import uz.pdp.eticketrailway.model.railway.Station;
import uz.pdp.eticketrailway.payload.place.PlaceDTO;
import uz.pdp.eticketrailway.payload.station.StationDTO;

import static uz.pdp.eticketrailway.utils.interfaces.Url.*;

@FeignClient(url =RAILWAY_BASE, name = "RailwayFeign")
public interface RailwayFeign {

    @PostMapping(RAILWAY_STATION)
    Station getStation(@RequestHeader("Accept-Language") String lan,@RequestBody StationDTO stationDTO);

    @PostMapping(RAILWAY_PLACE)
    Place getPlace(@RequestHeader("Accept-Language") String lan, @RequestBody PlaceDTO placeDTO);

}
