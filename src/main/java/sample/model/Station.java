package sample.model;

import ir.behinehsazan.gasStation.model.station.StationLogic;
import sample.model.base.BaseModel;

import java.util.HashMap;
import java.util.Map;

public class Station {


    private static final Station instance = new Station();
    private StationLogic stationLogic;

    public StationLogic getStationLogic() {
        return stationLogic;
    }
    public void setStationLogic(StationLogic stationLogic){
        this.stationLogic = stationLogic;
    }

    private Map<String, BaseModel> list = new HashMap<String, BaseModel>();

    private Station(){}

    public Map<String, BaseModel> getList() {
        return list;
    }



    public static Station getInstance() {
        return instance;
    }

    public void setList(Map<String, BaseModel> list) {
        this.list = list;
    }
}
