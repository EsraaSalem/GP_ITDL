package dataEntities;

public class InputSourcesSignificance {
	
	private String inputID_DB;
	private String inputSource;
	private double significanceRate;
	
	
	public InputSourcesSignificance() {
		super();
	}
	public InputSourcesSignificance(String inputID_DB, String inputSource, double significanceRate) {
		super();
		this.inputID_DB = inputID_DB;
		this.inputSource = inputSource;
		this.significanceRate = significanceRate;
	}
	public String getInputID_DB() {
		return inputID_DB;
	}
	public void setInputID_DB(String inputID_DB) {
		this.inputID_DB = inputID_DB;
	}
	public String getInputSource() {
		return inputSource;
	}
	public void setInputSource(String inputSource) {
		this.inputSource = inputSource;
	}
	public double getSignificanceRate() {
		return significanceRate;
	}
	public void setSignificanceRate(double significanceRate) {
		this.significanceRate = significanceRate;
	}
	

}
