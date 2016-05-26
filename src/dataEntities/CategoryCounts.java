package dataEntities;

public class CategoryCounts {
	
	private long categoryID_DB;
	private String userID_DB;
	private String category;
	private int count;
	
	
	public CategoryCounts() {
		super();
	}


	public CategoryCounts(long categoryID_DB, String userID_DB, String category, int count) {
		super();
		this.categoryID_DB = categoryID_DB;
		this.userID_DB = userID_DB;
		this.category = category;
		this.count = count;
	}


	public long getCategoryID_DB() {
		return categoryID_DB;
	}


	public void setCategoryID_DB(long categoryID_DB) {
		this.categoryID_DB = categoryID_DB;
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


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	

}
