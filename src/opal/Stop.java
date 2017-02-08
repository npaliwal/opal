package opal;

public class Stop {
	
	String id;
	float latitude;
	float longitude;
	String name;
	String code;
	String landmark;
	String image;
	boolean deleted;
	String suburb;
	
	int tap_in = 0;
	int tap_out = 0;
	int num_trip = 0;

	public Stop(String[] lineArr) {
		this.id = lineArr[7];
		this.name = lineArr[9];
		this.suburb = lineArr[10];
		this.latitude = Float.parseFloat(lineArr[11]);
		this.longitude = Float.parseFloat(lineArr[12]);
		
		String temp = this.name.substring(this.id.length() + 3, this.name.length());

		this.name = temp;
		this.landmark = "dummy_landmark";
		this.code = "dummy_code";
		
	}
	
	void updateTaps(String inStr, String outStr){
		this.tap_in += Integer.parseInt(inStr);
		this.tap_out += Integer.parseInt(outStr);
		this.num_trip++;
	}

	public String getString() {
		return id 
				+ "|" + name
				+ "|" + suburb
				+ "|" + latitude
				+ "|" + longitude
				+ "|" + tap_in
				+ "|" + tap_out
				+ "|" + num_trip
				;
	}
}
