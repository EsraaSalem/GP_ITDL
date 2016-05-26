package dataEntities;

public class UserPreferences {

	private String prefernceID_DB;
	private String userID_DB;
	private String category;
	private double rate;
	
	
	public UserPreferences() {
		super();
	}
	public UserPreferences(String prefernceID_DB, String userID_DB, String category, double rate) {
		super();
		this.prefernceID_DB = prefernceID_DB;
		this.userID_DB = userID_DB;
		this.category = category;
		this.rate = rate;
	}
	public String getPrefernceID_DB() {
		return prefernceID_DB;
	}
	public void setPrefernceID_DB(String prefernceID_DB) {
		this.prefernceID_DB = prefernceID_DB;
	}
	public String getUserID_DB() {
		return userID_DB;
	}
	public void setUserID_DB(String userID_DB) {
		this.userID_DB = userID_DB;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}
