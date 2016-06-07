package ar.edu.itba.paw.services;

public class PerformanceTimer {

	private boolean start;
	private long time;
	private String desc;
	
	public PerformanceTimer() {
		this.start= false;
		desc = "Nothing";
	}
	
	public void start(final String title) {
		time = System.currentTimeMillis();
		start = true;
		desc = title;
	}
	
	public long stop() {
		if(start) {
			start = false;
			long ans = System.currentTimeMillis()-time;
			System.out.println(desc + " time: " + ans);
			desc = "Nothing";
			return ans;
		}
		return -1;
	}
}
