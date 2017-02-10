package opal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Route {
	String id;
	String name;
	String code;
	boolean non_operation;
	String from;
	String to;
	String via;
	boolean reverse_route;
	boolean display_on_app;
	String reverse_route_group;
	boolean deleted;
	String direction;
	List<String> stop_point_ids;
	int suburbCount=0;
	String variant;
	
	Route(String _id, String _var, String _dir){
		this.id = _id;
		this.direction = _dir;
		this.stop_point_ids = new ArrayList<String>(0);
		this.deleted = false;
		this.reverse_route = false;
		this.display_on_app = true;
		this.non_operation = false;
		this.code = _var + _dir.substring(0, 1);
		this.variant = _var;
	}
	

	void addStop(String stopId){
		boolean addStop = true;
		for(String stop : stop_point_ids){
			if(stop.equals(stopId))
				addStop = false;
		}
		if(addStop){
			this.stop_point_ids.add(stopId);
		}
	}
	
	void setInfo(Map<String, Stop> stopMap){
		List<String> suburbs = new ArrayList<String>(0);
		for(String stopId : stop_point_ids){
			Stop stop = stopMap.get(stopId);
			if(stop != null && !suburbs.contains(stop.suburb)){
				suburbs.add(stop.suburb);
			}
		}
		this.via = "";
		this.suburbCount = suburbs.size();
		for(int i=0; i< suburbCount; i++){
			if(i==0){
				this.from = suburbs.get(i);
			}else if(i == suburbCount-1){
				this.to = suburbs.get(i);
			}else{
				this.via = this.via + suburbs.get(i) + " - ";
			}
		}
		
	}


	public String getString() {
		return id + "|" + variant 
				+ "|" + direction 
				+ "|" + stop_point_ids.size()
				+ "|" + suburbCount
				+ "|" + from 
				+ "|" + to 
				+ "|" + via;
	}


	public String getStopsString() {
		String retString = "";
		int position = 0;
		for(String stopId : stop_point_ids){
			//if(position > 0)
			//	retString = retString + "\n";
			retString = retString + this.id + "|" + this.variant + "|" + stopId + "|" + position + "\n";
			position++;
		}
		return retString;
	}

}
