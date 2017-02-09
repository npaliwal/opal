package src.opal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Helper {
	static String baseDir = "./data/complete/";
	//static String baseDir = "./data/sample/";

	static Map<String, Stop> stops = new HashMap<String, Stop>();
	static Map<String, Route> routes = new HashMap<String, Route>();
	static LinkedHashMap<String, Trip> trips = new LinkedHashMap<String, Trip>();
	static Map<String, Integer> suburbs = new HashMap<String, Integer>();
	//static Map<String, RouteStop> routeStops = new HashMap<String, RouteStop>();
	
	public static void main(String[] args) throws Exception{
		String[] lineArr = null;
		String routeId = null, tripId = null, stopId = null, suburb = null;
		Stop currStop = null;
		Route currRoute = null;
		Trip currTrip = null;
		//RouteStop currRouteStop = null;
		boolean isFirst = true;
		String prevTripId = "";
		try(BufferedReader br = new BufferedReader(new FileReader(baseDir + "raw_data.csv"))) {

		    for(String line; (line = br.readLine()) != null; ) {
		    	if(isFirst){
		    		isFirst = false;
		    		continue;
		    	}
		    	lineArr = line.split("\\|");
		    	if(lineArr != null){
		    		routeId = lineArr[1] +"("+lineArr[2]+ "-" + lineArr[3] + ")";
		    		tripId = lineArr[4];
			    	stopId = lineArr[7];
		    		suburb = lineArr[10];
		    		
		    		if(suburbs.get(suburb) == null){
		    			suburbs.put(suburb, 0);
		    		}
		    		
		    		
		    		currStop = stops.get(stopId);
		    		currRoute = routes.get(routeId);
		    		currTrip = trips.get(tripId);
		    		//currRouteStop = routeStops.get(stopId +"$"+ routeId);
		    		
		    		if(currStop == null){
		    			currStop = new Stop(lineArr);
		    			stops.put(stopId, currStop);
		    			suburbs.put(suburb, suburbs.get(suburb)+1);
		    		}
	    			stops.get(stopId).updateTaps(lineArr[13], lineArr[15]);
		    		
	    			
		    		if(currRoute == null){
		    			currRoute = new Route(lineArr[1], lineArr[2], lineArr[3]);
		    			routes.put(routeId, currRoute);
		    		}
		    		currRoute.addStop(stopId);
		    		
		    		String testDate = lineArr[0] + "," + lineArr[6];
		    		DateFormat formatter = new SimpleDateFormat("d/MMM/yy,HH:mm");
		    		Date stopTime = formatter.parse(testDate);
		    		
		    		if(currTrip == null) {
		    			currTrip = new Trip(tripId, currRoute, stopTime);
		    			trips.put(tripId, currTrip);
		    		}
		    		currTrip.addStopTimeData(new Pair(currStop, stopTime));
		    		

		    		//if(currRouteStop == null){
		    		//	currRouteStop = new RouteStop(stopId, routeId);
		    		//}
	    			if(!currRoute.direction.equals(lineArr[3])){
	    				System.err.println("Same route with difference direction--->" + line);
	    			}
		    	}
		    	//System.out.println(line);
		    }		    
		    
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "routes.csv"))) {
				for(Map.Entry<String, Route> entry : routes.entrySet()){
			    	entry.getValue().setInfo(stops);
			    	//entry.getValue().setStopPositions(routeStops);
					bw.write(entry.getValue().getString() + "\n");
			    }
				System.out.println("Done Routes");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "trips.csv"))) {
				for(Map.Entry<String, Trip> entry : trips.entrySet()){
			    	//entry.getValue().setStopPositions(routeStops);
					bw.write(entry.getValue().getString() + "\n");
			    }
				System.out.println("Done Trips");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "routeStops.csv"))) {
				for(Map.Entry<String, Route> entry : routes.entrySet()){
					bw.write(entry.getValue().getStopsString() );
			    }
				System.out.println("Done Route Stops");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "/stops.csv"))) {
				for(Map.Entry<String, Stop> entry : stops.entrySet()){
					bw.write(entry.getValue().getString() + "\n");
			    }
				System.out.println("Done Stops");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "suburbs.csv"))) {
		    	for(Map.Entry<String, Integer> entry : suburbs.entrySet()){
					bw.write(entry.getKey() + "|" + entry.getValue() + "\n");
			    }
				System.out.println("Done Suburbs");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    String[] limitSuburb = {"BONDI JUNCTION", "BONDI BEACH", "NORTH BONDI", "BONDI"};
		    ArrayList<Stop> limitStop = new ArrayList<>();
		    for(int i = 0; i < limitSuburb.length; i++)
		    	for(Map.Entry<String, Stop> entry : stops.entrySet()){
		    		if(entry.getValue().suburb.equals(limitSuburb[i]))
		    			limitStop.add(entry.getValue());
		    	}
		    
		    //System.out.println(limitStop.size());
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "/limit_stops.csv"))) {
				for(Stop entry : limitStop){
					bw.write(entry.getString() + "\n");
			    }
				System.out.println("Done Limit Stops");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    ArrayList<Route> limitRoute = new ArrayList<>();
		    for(int i = 0; i < limitStop.size(); i++)
		    	for(Map.Entry<String, Route> entry : routes.entrySet()){
		    		Route route = entry.getValue();
					if(!route.stop_point_ids.isEmpty()) {
		    			if(route.stop_point_ids.get(0).equals(limitStop.get(i).id))
		    				limitRoute.add(route);
		    		}
		    	}
		    //System.out.println(limitRoute.size());
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "/limit_routes.csv"))) {
				for(Route entry : limitRoute){
					bw.write(entry.getString() + "\n");
			    }
				System.out.println("Done Limit Routes");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    ArrayList<Trip> limitTrip = new ArrayList<>();
		    for(int i = 0; i < limitRoute.size(); i++)
		    	for(Map.Entry<String, Trip> entry : trips.entrySet()){
		    		Trip trip = entry.getValue();
					if(trip.route.id.equals(limitRoute.get(i).id))
		    			limitTrip.add(trip);
		    	}
		    //System.out.println(limitRoute.size());
		    
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(baseDir + "/limit_trips.csv"))) {
				for(Trip entry : limitTrip){
					bw.write(entry.getString() + "\n");
			    }
				System.out.println("Done Limit Trips");
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    
		    	
		}
	}
}
