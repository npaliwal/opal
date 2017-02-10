package transitdata;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import opal.Route;
import opal.Stop;

public class TransitData {
	@SerializedName("latest_version_id")
	int latestVersion;
	
	List<ZipgoCity> cities;
	
	
	class ZipgoCity{
		String name;
		List<Route> routes;
		List<Stop> stops;
	}
	
	class ZipgoRoute{
		String id;
		String name;
		String code;
		boolean non_operation;
		String via;
		boolean reverse_route;
		boolean display_on_app;
		String reverse_route_group;
		boolean deleted;
		String to;
		List<String> stop_point_ids;
		
	}
	
	class ZipgoStop{
		String id;
		float latitude;
		float longitude;
		String name;
		String code;
		String landmark;
		String image;
		boolean deleted;
	}
}
