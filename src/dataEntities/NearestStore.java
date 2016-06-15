package dataEntities;

public class NearestStore {

	
	private String storeName;
	private String userProductToBuy;
	private double lat;
	private double longt;
	
	
	public NearestStore() {
		super();
	}
	public NearestStore(String storeName, String userProductToBuy, double lat, double longt) {
		super();
		this.storeName = storeName;
		this.userProductToBuy = userProductToBuy;
		this.lat = lat;
		this.longt = longt;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getUserProductToBuy() {
		return userProductToBuy;
	}
	public void setUserProductToBuy(String userProductToBuy) {
		this.userProductToBuy = userProductToBuy;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLongt() {
		return longt;
	}
	public void setLongt(double longt) {
		this.longt = longt;
	}
	
	
}
