package dataEntities;

public class PairComparison {

	private int categoryID1;
	private int categoryID2;
	private double category1WeightVsCategory2;
	private String inputSource1;
	private String inputSource2;
	public String getInputSource1() {
		return inputSource1;
	}
	public void setInputSource1(String inputSource1) {
		this.inputSource1 = inputSource1;
	}
	public String getInputSource2() {
		return inputSource2;
	}
	public void setInputSource2(String inputSource2) {
		this.inputSource2 = inputSource2;
	}
	public int getCategoryID1() {
		return categoryID1;
	}
	public PairComparison() {
		super();
	}
	public void setCategoryID1(int categoryID1) {
		this.categoryID1 = categoryID1;
	}
	public int getCategoryID2() {
		return categoryID2;
	}
	@Override
	public String toString() {
		return "PairComparison [categoryID1=" + categoryID1 + ", categoryID2=" + categoryID2
				+ ", category1WeightVsCategory2=" + category1WeightVsCategory2 + "]";
	}
	public void setCategoryID2(int categoryID2) {
		this.categoryID2 = categoryID2;
	}
	public double getCategory1WeightVsCategory2() {
		return category1WeightVsCategory2;
	}
	public void setCategory1WeightVsCategory2(double category1WeightVsCategory2) {
		this.category1WeightVsCategory2 = category1WeightVsCategory2;
	}
	public PairComparison(int categoryID1, int categoryID2, double category1WeightVsCategory2) {
		super();
		this.categoryID1 = categoryID1;
		this.categoryID2 = categoryID2;
		this.category1WeightVsCategory2 = category1WeightVsCategory2;
	}
	
	
}
