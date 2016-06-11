package dataEntities;

public class UserInialWeights {

	
	private double inialWeight;
	private String categoryID;
	private String categoryName;
	private String userID;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public UserInialWeights() {
		super();
	}
	public UserInialWeights( String categoryID, double inialWeight) {
		super();
		this.inialWeight = inialWeight;
		this.categoryID = categoryID;
		this.categoryName = "";
	}
	public double getInialWeight() {
		return inialWeight;
	}
	public void setInialWeight(double inialWeight) {
		this.inialWeight = inialWeight;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "UserInialWeights [inialWeight=" + inialWeight + ", categoryID=" + categoryID + ", categoryName="
				+ categoryName + "]";
	}
	
	 
}
