package opal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Helper {
	static String baseDir = "./data/26th_30th/";
	//static String baseDir = "./data/sample/";

	static Map<String, Stop> stops = new HashMap<String, Stop>();
	static Map<String, Route> routes = new HashMap<String, Route>();
	static Map<String, Integer> suburbs = new HashMap<String, Integer>();
	//static Map<String, RouteStop> routeStops = new HashMap<String, RouteStop>();
	
	public static void main(String[] args) throws Exception{
		String[] lineArr = null;
		String routeId = null, stopId = null, suburb = null;
		Stop currStop = null;
		Route currRoute = null;
		//RouteStop currRouteStop = null;
		boolean isFirst = true;
		try(BufferedReader br = new BufferedReader(new FileReader(baseDir + "raw_data.csv"))) {

		    for(String line; (line = br.readLine()) != null; ) {
		    	if(isFirst){
		    		isFirst = false;
		    		continue;
		    	}
		    	lineArr = line.split("\\|");
		    	if(lineArr != null){
		    		routeId = lineArr[1] +"("+lineArr[2]+")";
		    		stopId = lineArr[7];
		    		suburb = lineArr[10];
		    		
		    		if(suburbs.get(suburb) == null){
		    			suburbs.put(suburb, 0);
		    		}
		    		
		    		
		    		currStop = stops.get(stopId);
		    		currRoute = routes.get(routeId);
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
		}
	}
}
