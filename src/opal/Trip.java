package src.opal;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
	
	String tripId;
	Route route;
	Date timeStart;
	ArrayList<Pair<Stop, Date>> stopTimeMap = new ArrayList<>();
	
	
	public Trip(String tripId, Route route, Date timeStart) {
		this.tripId = tripId;
		this.route = route;
		this.timeStart = timeStart;
	}
	
	void addStopTimeData(Pair<Stop, Date> stopTimePair) {
		stopTimeMap.add(stopTimePair);
	}
	
	public String getString() {
		return tripId + "|" + route.id
				+ "|" + timeStart
				+ "|" + stopTimeMap.size()
				+ "|" + stopTimeMap.get(0).getLeft().id
				+ "|" + stopTimeMap.get(stopTimeMap.size()-1).getLeft().id;
	}
	
}
