package dataEntities;

import com.google.apphosting.api.DatastorePb.CompiledCursor.Position;

public class CategoryCountOfSources {

	private String categoryID;
	private int categoryCount;
	private String categoryName;
	private int noteSrcCount;
	private int twitterSrcCount;
	private int facebookSrcCount;

	public CategoryCountOfSources(String categoryName, String categoryID, int noteSrcCount, int twitterSrcCount,
			int facebookSrcCount) {
		super();
		this.categoryID = categoryID;

		this.categoryName = categoryName;
		this.noteSrcCount = noteSrcCount;
		this.twitterSrcCount = twitterSrcCount;
		this.facebookSrcCount = facebookSrcCount;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public int getCategoryCount() {
		return categoryCount;
	}

	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	

	public void increamentCategoryCount(String flag)
	{
		
		if(flag.equals("note"))
		{
			noteSrcCount++;
		}
		else if(flag.equals("tweet"))
		{
			twitterSrcCount++;
		}
		else if(flag.equals("facebook"))
		{
			facebookSrcCount++;
		}
	}
	@Override
	public String toString() {
		return "CategoryCountOfSources [categoryID=" + categoryID + ", categoryCount=" + categoryCount
				+ ", categoryName=" + categoryName + ", noteSrcCount=" + noteSrcCount + ", twitterSrcCount="
				+ twitterSrcCount + ", facebookSrcCount=" + facebookSrcCount + "]";
	}

	public int getNoteSrcCount() {
		return noteSrcCount;
	}

	public void setNoteSrcCount(int noteSrcCount) {
		this.noteSrcCount = noteSrcCount;
	}

	public int getTwitterSrcCount() {
		return twitterSrcCount;
	}

	public void setTwitterSrcCount(int twitterSrcCount) {
		this.twitterSrcCount = twitterSrcCount;
	}

	public int getFacebookSrcCount() {
		return facebookSrcCount;
	}

	public void setFacebookSrcCount(int facebookSrcCount) {
		this.facebookSrcCount = facebookSrcCount;
	}

	public CategoryCountOfSources() {
		super();
		noteSrcCount = 0;
		twitterSrcCount = 0;
		facebookSrcCount = 0;
		categoryCount = 0;
	}

}
