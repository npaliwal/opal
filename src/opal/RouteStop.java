package src.opal;

public class RouteStop {
	
	String route_id;
	String stop_point_id;
	int position;
	
	public RouteStop(String stopId, String routeId) {
		this.route_id = routeId;
		this.stop_point_id = stopId;
		position = 0;
	}
	
}
