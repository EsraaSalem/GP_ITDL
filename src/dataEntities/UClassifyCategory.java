package dataEntities;

public class UClassifyCategory {

	
	private String category;
	private double count;
	
	
	public UClassifyCategory() {
		super();
	}
	public UClassifyCategory(String category, double count) {
		super();
		this.category = category;
		this.count = count;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	
	
	
}
